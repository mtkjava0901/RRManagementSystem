package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	// ログインフォーム
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	

	// 会員登録
	@GetMapping("/register")
	public String register() {
		return "register";
	}


}
