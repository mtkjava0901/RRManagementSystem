package com.example.app.service;

import java.util.List;

import com.example.app.domain.Book;
import com.example.app.form.BookForm;

public interface BookService {

	/* ********
	 * CRUD
	 * ********/

	// 書籍詳細
	Book selectById(Integer id);

	// 書籍追加
	void insert(Book book);

	// 書籍編集
	void update(Book book);

	// 書籍削除(論理削除)
	void delete(Integer id);

	/* ********
	 * 検索 & ページネーション
	 * ********/

	// 検索結果件数取得
	int countBooks(String keyword, Integer genreId);

	// 検索（ページネーション）
	List<Book> searchBooks(
			String keyword,
			Integer genreId,
			String sort,
			int page,
			int size);

	/* ********
	 * Add Book
	 * ********/

	// マニュアル書籍追加(/book/add)
	Integer createManualBook(BookForm form);

	// 本棚へ追加(未使用推奨)
	// void add(Integer userId, Integer bookId);

}
