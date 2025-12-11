package com.example.app.service;

import java.util.List;

import com.example.app.domain.User;

public interface UserService {
	
	// ユーザー一覧
	List<User> getUserList();
	
	// ユーザーID取得
	User getUserById(Integer id);
	
	// ユーザー追加
	void addUser(User user);
	
	// ユーザー編集
	void editUser(User user);
	
	// ユーザー論理削除
	void deleteUser(Integer id);
	
	// 同じログインID(Email)が存在するか判定
	boolean existsByLoginId(String loginId);
	
	// DBのUserを1件取得、パスワードが一致するかチェック
	User findByLoginId(String loginId);
	
	// パスワード認証
	public boolean isCorrectIdAndPassword(String loginId, String loginPass);
	
	// BCrypt.checkpw
	String getPasswordHashByLoginId(String loginId);
}
