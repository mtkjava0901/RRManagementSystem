package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.UserBook;
import com.example.app.enums.ReadingStatus;

@Mapper
public interface UserBookMapper {

	// 1件取得
	UserBook selectById(@Param("id") Integer id);

	// ユーザー本棚一覧取得
	List<UserBook> selectByUserId(@Param("userId") Integer userId);

	// 新規登録(UserId, BookId, Status, Rating, Memo)
	int insert(UserBook userBook);

	// 更新(status, rationg, memo)
	int update(UserBook userBook);

	// 本棚登録解除(論理削除)
	int softDelete(@Param("id") Integer id);

	// ユーザー登録書籍削除(物理削除)
	int hardDelete(@Param("id") Integer id);

	// 検索(ソート・キーワード・ステータス・ページング用)
	List<UserBook> search(
			@Param("userId") Integer userId,
			@Param("sort") String sort,
			@Param("status") ReadingStatus status,
			@Param("keyword") String keyword,
			@Param("offset") int offset,
			@Param("limit") int limit);

	// 件数カウント（ページング用）
	int countSearch(
			@Param("userId") Integer userId,
			@Param("status") ReadingStatus status,
			@Param("keyword") String keyword);

	// 本棚に存在(is_deleted=0)
	UserBook selectActive(
			@Param("userId") Integer userId,
			@Param("bookId") Integer bookId);

	// 論理削除済み(is_deleted=1)
	UserBook selectDeleted(
			@Param("userId") Integer userId,
			@Param("bookId") Integer bookId);

	// 論理削除レコード復活(is_deleted1→0)
	int restore(
			@Param("id") Integer id);

	// 本棚に追加(API/管理者書籍)
	List<UserBook> selectAddedBooksByUser(Integer userId);

	// ユーザー作成書籍
	List<UserBook> selectManualBooksByUser(Integer userId);

	// 重複追加回避
	// int exists(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

	// 論理削除含め存在チェック
	// int existsAll(
	//		@Param("userId") Integer userId,
	//		@Param("bookId") Integer bookId);

}
