package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class RecommendController {
	
	// レコメンド
	@GetMapping("/book/recommend")
	public String bookRecommend() {
		return "book/recommend";
	}

}
