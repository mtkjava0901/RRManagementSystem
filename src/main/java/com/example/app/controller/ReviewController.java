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
	
	// レビュー詳細
	@GetMapping("/book/review/detail")
	public String bookReviewDetail() {
		return "book/review/detail";
	}
	
	// 個別レビュー一覧
	@GetMapping("/book/review/detail-list")
	public String bookReviewDetailList() {
		return "book/review/detail-list";
	}

}
