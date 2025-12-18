package com.example.app.controller;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;
import com.example.app.domain.User;
import com.example.app.domain.UserBook;
import com.example.app.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	// private final UserBookService userBookService;
	private final HttpSession session;

	// レビュー投稿ページ表示
	@GetMapping("/add")
	public String showAddForm(
			@RequestParam("bookId") Integer bookId,
			@SessionAttribute(value = "user", required = false) User user, // null警告回避
			RedirectAttributes ra,
			Model model) {

		if (user == null) {
			return "redirect:/login";
		}

		Integer userId = user.getId();
		UserBook ub = reviewService.findReviewableUserBook(userId, bookId);

		if (ub == null) {
			ra.addFlashAttribute("errorMessage", "この書籍は本棚に追加されていません。先に本棚に追加してください。");
			return "redirect:/book/list";
		}

		// レビュー可能か判定
		if (!reviewService.isReviewable(ub)) {
			ra.addFlashAttribute(
					"errorMessage",
					"手動登録した書籍はレビューできません");
			return "redirect:/book/detail/" + ub.getId();
		}

		ReviewForm form = new ReviewForm();
		form.setUserBookId(ub.getId());
		form.setBookId(ub.getBook().getId());
		model.addAttribute("reviewForm", form);
		model.addAttribute("username", user.getName());

		return "book/review/add";
	}

	// レビュー投稿
	@PostMapping("/add")
	public String addReview(
			@Valid @ModelAttribute("reviewForm") ReviewForm form,
			@SessionAttribute(value = "user", required = false) User user,
			BindingResult bindingResult,
			RedirectAttributes ra,
			Model model) {

		if (user == null) {
			return "redirect:/login";
		}

		Integer userId = user.getId();
		Integer userBookId = form.getUserBookId();

		// UserBookを取得(null/違うユーザーならリダイレクト)
		UserBook ub = reviewService.findReviewableUserBookByUserBookId(userId, form.getUserBookId());
		if (ub == null || !ub.getUserId().equals(userId)) {
			return "redirect:/book/list";
		}

		// レビュー可能か判定(MANUAL不可)
		if (!reviewService.isReviewable(ub)) {
			ra.addFlashAttribute(
					"errorMessage",
					"手動登録した書籍はレビューできません");
			return "redirect:/book/detail/" + userBookId;
		}

		// バリデーションエラー
		if (bindingResult.hasErrors()) {
			model.addAttribute("username", user.getName());
			return "book/review/add";
		}

		reviewService.addReview(userId, form);

		return "redirect:/book/review/detail-list?bookId=" + ub.getBookId();
	}

	// 全レビュー新着タイムライン
	@GetMapping("/list")
	public String allReviews(
			@SessionAttribute(value = "user", required = false) User user,
			Model model) {

		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		//List<Review> reviews = reviewService.getAllReviews();
		//model.addAttribute("reviews", reviews);

		model.addAttribute("reviews", reviewService.getAllReviews());

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
