package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ReviewController {
	
	// レビューする
	@GetMapping("/book/review/add")
	public String addBookReview() {
		return "book/review/add";
	}

	// 新着レビュー(※要日時記載)
	@GetMapping("/book/review/list")
	public String bookReviewList() {
		return "book/review/list";
	}

}
