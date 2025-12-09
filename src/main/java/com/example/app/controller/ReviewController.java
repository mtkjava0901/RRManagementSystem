package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
