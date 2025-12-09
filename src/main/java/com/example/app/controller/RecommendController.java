package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendController {
	
	// レコメンド
	@GetMapping("/book/recommend")
	public String bookRecommend() {
		return "book/recommend";
	}

}
