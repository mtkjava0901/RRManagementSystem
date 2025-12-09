package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
	
	// 管理者ログイン
	@GetMapping("/admin/login")
	public String adminLogin() {
		return "admin/login";
	}

}
