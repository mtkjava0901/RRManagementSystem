package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Book;

@Mapper
public interface BookMapper {

	/* ********
	 * CRUD
	 **********/

	// 書籍一覧
	List<Book> selectAll();

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
	 **********/

	// 検索件数を取得
	int countBySearchConditions(
			@Param("keyword") String keyword,
			@Param("genreId") Integer genreId);

	// 検索結果を取得
	List<Book> findBySearchConditions(
			@Param("keyword") String keyword,
			@Param("genreId") Integer genreId,
			@Param("sort") String sort,
			@Param("offset") Integer offset,
			@Param("limit") Integer limit);

}
