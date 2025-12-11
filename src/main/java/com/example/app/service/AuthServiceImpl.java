package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.example.app.domain.Admin;
import com.example.app.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AdminService adminService;
	private final UserService userService;

	// 管理者ログイン認証
	@Override
	public Admin authenticateAdmin(String loginId, String loginPass, Errors errors) {

		if (errors.hasErrors()) {
			return null;
		}
		
		Admin dbAdmin = adminService.findByLoginId(loginId);
		
		if (dbAdmin == null) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
			return null;
		}

		// 認証チェック
		boolean authenticated = adminService.isCorrectIdAndPassword(loginId, loginPass);
		if (!authenticated) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
			return null;
		}
		// 認証成功(Adminを返す)
		return dbAdmin;
	}
	

	// ユーザーログイン認証
	@Override
	public User authenticateUser(String loginId, String loginPass, Errors errors) {
		
		if (errors.hasErrors()) {
			return null;
		}

		// DBからloginIdでStudentを取得
		User dbUser = userService.findByLoginId(loginId);

		// ログインIDが存在しない
		if (dbUser == null) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
			return null;
		}

		// パスワードチェック
		boolean authenticated = userService.isCorrectIdAndPassword(loginId, loginPass);
		if (!authenticated) {
			errors.rejectValue("loginId", "error.incorrect_id_password",
					"ログインIDまたはパスワードが正しくありません。");
			return null;
		}
		return dbUser;
	}
}
