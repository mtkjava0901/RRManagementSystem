package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminLoginController {
	
	// 管理者ログイン
	@GetMapping("/admin/login")
	public String adminLogin() {
		return "admin/login";
	}

}
