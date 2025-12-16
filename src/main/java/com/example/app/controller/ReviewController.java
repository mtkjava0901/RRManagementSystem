package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

	private final HttpSession session;

	// レビューする
	@GetMapping("/book/review/add")
	public String addBookReview(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "book/review/add";
	}

	// 新着レビュー(※要日時記載)
	@GetMapping("/book/review/list")
	public String bookReviewList(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "book/review/list";
	}

	// レビュー詳細
	@GetMapping("/book/review/detail")
	public String bookReviewDetail(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "book/review/detail";
	}

	// 個別レビュー一覧
	@GetMapping("/book/review/detail-list")
	public String bookReviewDetailList(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "book/review/detail-list";
	}

}
