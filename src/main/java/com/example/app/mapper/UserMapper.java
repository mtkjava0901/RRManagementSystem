package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.User;

@Mapper
public interface UserMapper {

	// ユーザーページ用マッピング

	// ユーザー一覧
	List<User> selectAll();

	// ユーザーID取得
	User selectById(Integer id);

	// ユーザー追加
	void insert(User user);

	// ユーザー編集
	void update(User user);

	// ユーザー倫理削除
	void delete(Integer id);

	// 同じログインID(Email)が存在するか判定
	int countByLoginId(String loginId);

	// 指定ID以外で同ログインIDが存在するか判定（更新時重複チェック用/今回は不要）
	// int countByLoginIdExcludingId(@Param("loginId") String loginId,
	// 		@Param("id") Integer id);

	// DBのUserを1件取得、パスワードが一致するかチェック
	User findByLoginId(@Param("loginId") String loginId);

}
