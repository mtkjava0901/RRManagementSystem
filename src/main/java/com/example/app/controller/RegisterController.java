package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
	
//会員登録
	@GetMapping("/register")
	public String register() {
		return "register";
	}

}
