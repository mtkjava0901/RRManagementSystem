package com.example.app.service;

import java.util.List;

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

	// 論理削除
	int softDelete(Integer id);

	// 物理削除
	int hardDelete(Integer id);
	
	// ページング検索
	PaginatedResult<UserBook> search(
      Integer userId,
      String sort,
      ReadingStatus status,
      String keyword,
      int page
  );

}
