package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Book;
import com.example.app.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {

	private final BookMapper mapper;

	/* ********
	 * CRUD
	 * ********/

	// 書籍詳細
	@Override
	public Book selectById(Integer id) {
		return mapper.selectById(id);
	}

	// 書籍追加
	@Override
	public void insert(Book book) {
		mapper.insert(book);
	}

	// 書籍編集
	@Override
	public void update(Book book) {
		mapper.update(book);
	}

	// 書籍削除(論理削除)
	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	/* ********
	 * 検索 & ページネーション
	 * ********/

	// 検索結果件数取得
	@Override
	public int countBooks(String keyword, Integer genreId) {
		return mapper.countBySearch(keyword, genreId);
	}

	// 検索（ページネーション）
	@Override
	public List<Book> searchBooks(String keyword, Integer genreId, String sort, int page, int size) {

		int offset = (page - 1) * size;

		return mapper.findBySearch(keyword, genreId, sort, offset, size);
	}

}
