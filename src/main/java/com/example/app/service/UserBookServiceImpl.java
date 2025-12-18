package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.app.domain.Book;
import com.example.app.domain.UserBook;
import com.example.app.enums.BookSource;
import com.example.app.enums.ReadingStatus;
import com.example.app.mapper.BookMapper;
import com.example.app.mapper.UserBookMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

	private final UserBookMapper userBookMapper;
	private final BookMapper bookMapper;

	// 注：exists/getByUserAndBook/Map使用は旧設計(バグる可能性有)

	// loginUserIdを取得
	private Integer getLoginUserId() {
		return (Integer) RequestContextHolder.currentRequestAttributes()
				.getAttribute("loginUserId", RequestAttributes.SCOPE_SESSION);
	}

	// 1件取得(アクティブID(画面専用)/is_deleted=0)
	@Override
	public UserBook getActiveById(Integer userBookId) {
		return userBookMapper.selectActiveById(userBookId);
	}

	// 1件取得(管理用途/is_deleted無し)
	@Override
	public UserBook getAnyById(Integer userBookId) {
		return userBookMapper.selectAnyById(userBookId);
	}

	// 本棚一覧取得(画面用)
	@Override
	public List<UserBook> getActiveByUserId(Integer userId) {
		return userBookMapper.selectActiveByUserId(userId);
	}

	// 登録
	@Override
	public int add(Integer userId, Integer bookId) {

		// ログインユーザー以外への登録は禁止
		if (!userId.equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		// すでにアクティブなら何もしないor例外
		if (exists(userId, bookId)) {
			throw new RuntimeException("すでに本棚に追加されています");
		}

		// 論理削除済みがある→復活
		UserBook deleted = userBookMapper.selectDeleted(userId, bookId);
		if (deleted != null) {
			userBookMapper.restore(deleted.getId());
			return 1;
		}

		// 新規追加
		UserBook ub = new UserBook();
		ub.setUserId(userId);
		ub.setBookId(bookId);
		ub.setSource(BookSource.SEARCH);
		ub.setStatus(ReadingStatus.UNSPECIFIED);
		ub.setRating(null);
		ub.setMemo(null);
		ub.setIsDeleted(false);

		return userBookMapper.insert(ub);
	}

	// 更新
	@Override
	public int update(Integer id, Integer rating, ReadingStatus status, String memo) {

		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = userBookMapper.selectActiveById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		UserBook ub = new UserBook();
		ub.setId(id);
		ub.setRating(rating);
		ub.setStatus(status);
		ub.setMemo(memo);
		return userBookMapper.update(ub);
	}

	// 本棚から外す(削除操作は管理用途/selectAnyById)
	@Override
	public void remove(Integer userBookId) {
		UserBook db = userBookMapper.selectAnyById(userBookId);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		boolean isManualBook = db.getBook().getManual();

		if (isManualBook) {
			userBookMapper.hardDelete(userBookId); // MANUALは物理削除
		} else {
			userBookMapper.softDelete(userBookId); // SEARCHは論理削除
		}
	}

	// ページング検索
	@Override
	public PaginatedResult<UserBook> search(
			Integer userId,
			String sort,
			ReadingStatus status,
			String keyword,
			int page) {

		final int PAGE_SIZE = 10;

		// pageが1未満なら1に補正
		int safePage = Math.max(page, 1);
		int offset = (safePage - 1) * PAGE_SIZE;

		// 件数取得
		int totalCount = userBookMapper.countSearch(userId, status, keyword);
		int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

		// データ取得
		List<UserBook> list = userBookMapper.search(userId, sort, status, keyword, offset, PAGE_SIZE);

		return new PaginatedResult<>(list, totalCount, totalPages, safePage);
	}

	// MANUAL登録
	public void addManual(Integer userId, Book book) {

		// ログインユーザー検証
		if (!userId.equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		// Bookを作る
		bookMapper.insert(book);

		// UserBookを作る
		UserBook ub = new UserBook();
		ub.setUserId(userId);
		ub.setBookId(book.getId());
		ub.setSource(BookSource.MANUAL);
		ub.setStatus(ReadingStatus.UNSPECIFIED);
		ub.setIsDeleted(false);

		userBookMapper.insert(ub);
	}

	// 本棚に追加した書籍（管理者/API書籍）
	@Override
	public List<UserBook> getAddedBooksByUser(Integer userId) {
		return userBookMapper.selectAddedBooksByUser(userId);
	}

	// ユーザー作成書籍
	@Override
	public List<UserBook> getManualBooksByUser(Integer userId) {
		return userBookMapper.selectManualBooksByUser(userId);
	}

	// 重複登録チェック アクティブなUserBookが存在するか(is_deleted=0)
	// (int→boolean変換)
	@Override
	public boolean exists(Integer userId, Integer bookId) {
		return userBookMapper.exists(userId, bookId) > 0;
	}

	/* *****************
	 * 未使用
	 * *****************
	// 論理削除(内部用)
	@Override
	public int softDelete(Integer id) {
	
		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = mapper.selectById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}
	
		return mapper.softDelete(id);
	}
	
	// 物理削除(内部用)
	@Override
	public int hardDelete(Integer id) {
	
		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = mapper.selectById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}
	
		return mapper.hardDelete(id);
	}
	
		// UserId + bookIdからUserBookを取得
	@Deprecated
	public UserBook getByUserAndBook(Integer userId, Integer bookId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("bookId", bookId);
		return userBookMapper.selectActive(userId, bookId);
	}
	
		// SEARCH登録
	public void addFromSearch(Integer userId, Integer bookId) {
	
		UserBook ub = new UserBook();
		ub.setUserId(userId);
		ub.setBookId(bookId);
		ub.setSource(BookSource.SEARCH);
		ub.setStatus(ReadingStatus.UNSPECIFIED);
		ub.setIsDeleted(false);
	
		userBookMapper.insert(ub);
	}
	
	
	***************** *
	未使用
	***************** */

}
