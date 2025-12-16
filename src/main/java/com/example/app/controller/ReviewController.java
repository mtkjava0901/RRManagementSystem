package com.example.app.controller;

import java.security.Principal;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;
import com.example.app.domain.User;
import com.example.app.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final HttpSession session;

	// レビュー投稿ページ表示
	@GetMapping("/add")
	public String showAddForm(
			@RequestParam Integer bookId,
			Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		ReviewForm form = new ReviewForm();
		form.setBookId(bookId);
		model.addAttribute("reviewForm", form);
		return "book/review/add";
	}

	// レビュー投稿ページ
	@PostMapping("/add")
	public String addReview(@Valid @ModelAttribute("reviewForm") ReviewForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "book/review/add";
		}

		// ログインユーザーIDを取得（Principalを利用）
		Integer userId = Integer.valueOf(principal.getName());

		try {
			reviewService.addReview(userId, form);
		} catch (IllegalArgumentException e) {
			bindingResult.reject("duplicate", e.getMessage());
			return "book/review/add";
		}

		return "redirect:/book/review/detail-list?bookId=" + form.getBookId();
	}

	// 全レビュー新着タイムライン
	@GetMapping("/list")
	public String allReviews(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		List<Review> reviews = reviewService.getAllReviews();
		model.addAttribute("reviews", reviews);

		return "book/review/list";
	}

	// レビュー詳細(1件)
	@GetMapping("/detail")
	public String reviewDetail(
			@RequestParam Integer reviewId,
			Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		Review review = reviewService.getReview(reviewId);
		model.addAttribute("review", review);

		return "book/review/detail";
	}

	// 書籍ごとのレビュー一覧
	@GetMapping("/detail-list")
	public String reviewList(
			@RequestParam Integer bookId,
			Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}
		
		List<Review> reviews = reviewService.getReviewsByBook(bookId);
    model.addAttribute("reviews", reviews);

		return "book/review/detail-list";
	}

}
