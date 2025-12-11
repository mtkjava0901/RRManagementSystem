package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// ホーム画面
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}

}
