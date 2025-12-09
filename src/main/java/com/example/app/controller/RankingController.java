package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class RankingController {
	
	// ランキング
	@GetMapping("/book/ranking")
	public String bookRanking() {
		return "book/ranking";
	}

}
