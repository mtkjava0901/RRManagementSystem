package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.UserBook;
import com.example.app.enums.ReadingStatus;
import com.example.app.service.PaginatedResult;
import com.example.app.service.UserBookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class UserBookController {

	private final UserBookService userBookService;
	private final HttpSession session;

	// ログイン中はユーザーIDを取得
	private Integer getLoginUserId() {
		return (Integer) session.getAttribute("loginUserId");
	}

	// マイ本棚
	@GetMapping("/list")
	public String showBookList(
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "status", required = false) ReadingStatus status,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		PaginatedResult<UserBook> books = userBookService.search(userId, sort, status, keyword, page);

		model.addAttribute("books", books);
		model.addAttribute("userId", userId);
		return "book/list";
	}

	// 本棚への追加
	@GetMapping("/add-booklist")
	public String addBookList(
			@RequestParam("bookId") Integer bookId) {

		// 未ログイン防止
		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		userBookService.add(userId, bookId);
		return "redirect:/book/list";
	}

	// 書籍詳細
	@GetMapping("/detail/{id}")
	public String bookDetail(
			@PathVariable("id") Integer id,
			Model model) {

		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		UserBook ub = userBookService.getById(id);

		// 不正アクセス防止
		if (ub == null || !ub.getUserId().equals(userId)) {
			return "redirect:/book/list";
		}

		model.addAttribute("book", ub);
		return "book/detail";
	}

	// 書籍更新処理
	@PostMapping("/update")
	public String update(
			@RequestParam("id") Integer id,
			@RequestParam("rating") Integer rating,
			@RequestParam("status") ReadingStatus status,
			@RequestParam("memo") String memo) {

		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		UserBook ub = userBookService.getById(id);
		if (ub == null || !ub.getUserId().equals(userId)) {
			return "redirect:/book/list";
		}

		userBookService.update(id, rating, status, memo);

		return "redirect:/book/detail/" + id;
	}

	// 書籍からの削除ボタン（論理削除）
	@PostMapping("/soft-delete")
	public String softDelete(
			@RequestParam("id") Integer id) {

		Integer userId = getLoginUserId();
		if (userId == null) return "redirect:/login";
		
		UserBook ub = userBookService.getById(id);
    if (ub == null || !ub.getUserId().equals(userId)) {
        return "redirect:/book/list";
    }
		
		userBookService.softDelete(id);

		return "redirect:/book/list";
	}

	// マニュアル登録書籍の削除（物理削除）
	@PostMapping("/hard-delete")
	public String hardDelete(
			@RequestParam("id") Integer id) {

		Integer userId = getLoginUserId();
		if (userId == null) return "redirect:/login";
		
		UserBook ub = userBookService.getById(id);
    if (ub == null || !ub.getUserId().equals(userId)) {
        return "redirect:/book/list";
    }
		
		userBookService.hardDelete(id);

		return "redirect:/book/list";
	}

	// 書籍詳細(after/確認用)
	@GetMapping("/detail-after")
	public String bookDetailAfter() {
		return "book/detail-after";
	}

	// 本詳細ページ(テスト/確認用)
	@GetMapping("/detail-test")
	public String bookDetailTest() {
		return "book/detail-test";
	}

}
