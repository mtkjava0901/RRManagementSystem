package com.example.app.service;

import com.example.app.domain.Admin;

public interface AdminService {
	
	// パスワード認証
	boolean isCorrectIdAndPassword(String loginId, String loginPass);
	
	// DBからLoginId取得
	Admin findByLoginId(String loginId);

	// BCrypt.checkpw
	String getPasswordHashByLoginId(String loginId);

}
