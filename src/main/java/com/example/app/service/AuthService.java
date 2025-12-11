package com.example.app.service;

import org.springframework.validation.Errors;

import com.example.app.domain.Admin;
import com.example.app.domain.User;

public interface AuthService {
	
	// 管理者ログイン認証
	Admin authenticateAdmin(String loginId, String loginPass, Errors erros);
	
	// ユーザーログイン認証
	User authenticateUser(String loginId, String loginPass, Errors errors);

}
