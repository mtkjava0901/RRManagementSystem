package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.app.domain.UserBook;
import com.example.app.enums.ReadingStatus;
import com.example.app.mapper.UserBookMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

	private final UserBookMapper mapper;

	// loginUserIdを取得
	private Integer getLoginUserId() {
		return (Integer) RequestContextHolder.currentRequestAttributes()
				.getAttribute("loginUserId", RequestAttributes.SCOPE_SESSION);
	}

	// 1件取得
	@Override
	public UserBook getById(Integer id) {
		return mapper.selectById(id);
	}

	// 本棚一覧取得
	@Override
	public List<UserBook> getByUserId(Integer userId) {
		return mapper.selectByUserId(userId);
	}

	// 登録
	@Override
	public int add(Integer userId, Integer bookId) {

		// ログインユーザー以外への登録は禁止
		if (!userId.equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		int exists = mapper.existsAll(userId, bookId);
		if (exists > 0) {
			mapper.restore(userId, bookId);
			return 1;
		}

		UserBook ub = new UserBook();
		ub.setUserId(userId);
		ub.setBookId(bookId);
		ub.setStatus(ReadingStatus.UNSPECIFIED);
		ub.setRating(null);
		ub.setMemo(null);

		return mapper.insert(ub);
	}

	// 更新
	@Override
	public int update(Integer id, Integer rating, ReadingStatus status, String memo) {

		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = mapper.selectById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		UserBook ub = new UserBook();
		ub.setId(id);
		ub.setRating(rating);
		ub.setStatus(status);
		ub.setMemo(memo);
		return mapper.update(ub);
	}

	// 論理削除
	@Override
	public int softDelete(Integer id) {

		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = mapper.selectById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		return mapper.softDelete(id);
	}

	// 物理削除
	@Override
	public int hardDelete(Integer id) {

		// 対象データ取得、ログインユーザー以外ならエラー
		UserBook db = mapper.selectById(id);
		if (db == null || !db.getUserId().equals(getLoginUserId())) {
			throw new RuntimeException("不正なアクセスです。");
		}

		return mapper.hardDelete(id);
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
		int offset = (page - 1) * PAGE_SIZE;

		// 件数取得
		int totalCount = mapper.countSearch(userId, status, keyword);
		int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

		// データ取得
		List<UserBook> list = mapper.search(userId, sort, status, keyword, offset, PAGE_SIZE);

		return new PaginatedResult<>(list, totalCount, totalPages, page);
	}

}
