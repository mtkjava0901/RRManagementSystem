package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Book;
import com.example.app.domain.BookGenre;
import com.example.app.form.BookForm;
import com.example.app.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {

	private final BookMapper bookMapper;
	// private final UserBookMapper userBookMapper;

	/* ********
	 * CRUD
	 * ********/

	// 書籍詳細
	@Override
	public Book selectById(Integer id) {
		return bookMapper.selectById(id);
	}

	// 書籍追加
	@Override
	public void insert(Book book) {
		bookMapper.insert(book);
	}

	// 書籍編集
	@Override
	public void update(Book book) {
		bookMapper.update(book);
	}

	// 書籍削除(論理削除)
	@Override
	public void delete(Integer id) {
		bookMapper.delete(id);
	}

	/* ********
	 * 検索 & ページネーション
	 * ********/

	// 検索結果件数取得
	@Override
	public int countBooks(String keyword, Integer genreId) {
		return bookMapper.countBySearch(keyword, genreId);
	}

	// 検索（ページネーション）
	@Override
	public List<Book> searchBooks(String keyword, Integer genreId, String sort, int page, int size) {

		int offset = (page - 1) * size;

		return bookMapper.findBySearch(keyword, genreId, sort, offset, size);
	}

	/* ********
	 * Add Book
	 * ********/

	// マニュアル書籍追加(/book/add)
	@Override
	public Integer createManualBook(BookForm form) {

		Book book = new Book();
		book.setTitle(form.getTitle());
		book.setAuthor(form.getAuthor());
		book.setPublisher(form.getPublisher());
		book.setPublisherAt(form.getPublishedAt());

		BookGenre genre = new BookGenre();
		genre.setId(form.getGenreId());
		book.setBookGenre(genre);

		// 業務用ルール
		book.setManual(false);
		book.setStatus("ACT");

		bookMapper.insert(book);

		return book.getId();
	}

	/*
	// 本棚へ追加(未使用推奨)
	@Override
	public void add(Integer userId, Integer bookId) {
	
		// ① 既に存在するか（論理削除含む）
		int countAll = userBookMapper.existsAll(userId, bookId);
	
		if (countAll > 0) {
			// ② 論理削除されているなら復活
			int activeCount = userBookMapper.exists(userId, bookId);
			if (activeCount == 0) {
				userBookMapper.restore(userId, bookId);
				return;
			}
			// ③ 既に追加済み（例外）
			throw new DuplicateKeyException("その本は既に本棚に追加済みです");
		}
	
		// ④ 新規追加
		UserBook userBook = new UserBook();
		userBook.setUserId(userId);
		userBook.setBookId(bookId);
		userBook.setStatus(ReadingStatus.UNREAD); // 初期状態
		userBook.setRating(null);
		userBook.setMemo(null);
	
		userBookMapper.insert(userBook);
	}
	*/

}
