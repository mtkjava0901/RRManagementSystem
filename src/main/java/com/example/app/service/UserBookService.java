package com.example.app.service;

import java.util.List;

import com.example.app.domain.Book;
import com.example.app.domain.UserBook;
import com.example.app.enums.ReadingStatus;

public interface UserBookService {

	// 1件取得
	UserBook getById(Integer id);

	// 本棚一覧取得
	List<UserBook> getByUserId(Integer userId);

	// 登録
	public int add(Integer userId, Integer bookId);

	// 更新
	int update(Integer id, Integer rating, ReadingStatus status, String memo);

	// 本棚から外す（論理物理両対応）
	void remove(Integer userBookId);

	// SEARCH登録
	void addFromSearch(Integer userId, Integer bookId);

	// MANUAL登録
	void addManual(Integer userId, Book book);

	// 論理削除(内部用)
	// int softDelete(Integer id);

	// 物理削除(内部用)
	// int hardDelete(Integer id);

	// ページング検索
	PaginatedResult<UserBook> search(
			Integer userId,
			String sort,
			ReadingStatus status,
			String keyword,
			int page);

	// 本棚に追加した書籍（管理者/API書籍）
	List<UserBook> getAddedBooksByUser(Integer userId);

	// ユーザー作成書籍
	List<UserBook> getManualBooksByUser(Integer userId);

}
