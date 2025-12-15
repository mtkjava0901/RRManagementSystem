package com.example.app.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) ReadingStatus status,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page,
			Model model) {

		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		// 本棚に追加した書籍(検索・ページング対象)
		PaginatedResult<UserBook> addedBooks = userBookService.search(userId, sort, status, keyword, page);

		// ユーザー作成書籍(シンプル一覧)
		List<UserBook> manualBooks = userBookService.getManualBooksByUser(userId);

		model.addAttribute("addedBooks", addedBooks);
		model.addAttribute("manualBooks", manualBooks);

		// 既存パラメータ(画面維持用)
		model.addAttribute("sort", sort);
		model.addAttribute("status", status);
		model.addAttribute("keyword", keyword);
		model.addAttribute("userId", userId);
		return "book/list";
	}

	// 本棚への追加（redirect後も同条件で再検索）
	@PostMapping("/add-booklist")
	public String addBookList(
			@RequestParam Integer bookId,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer genreId,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			RedirectAttributes ra) {

		Integer userId = getLoginUserId();
		if (userId == null)
			return "redirect:/login";

		try {
			userBookService.add(userId, bookId);

			ra.addFlashAttribute("successMessage", "本棚に追加しました");

			// クエリパラメータ(自動URLエンコード)
			ra.addAttribute("doSearch", 1);
			ra.addAttribute("keyword", keyword);
			ra.addAttribute("genreId", genreId);
			ra.addAttribute("sort", sort);
			ra.addAttribute("page", page);

			return "redirect:/book/search";

		} catch (DuplicateKeyException e) {
			ra.addFlashAttribute(
					"errorMessage", e.getMessage());

			ra.addAttribute("doSearch", 1);
			ra.addAttribute("keyword", keyword);
			ra.addAttribute("genreId", genreId);
			ra.addAttribute("sort", sort);
			ra.addAttribute("page", page);

			return "redirect:/book/search";
		}
	}

	// 書籍詳細
	@GetMapping("/detail/{id}")
	public String bookDetail(
			@PathVariable Integer id,
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
			@RequestParam Integer id,
			@RequestParam Integer rating,
			@RequestParam ReadingStatus status,
			@RequestParam String memo) {

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

	// 書籍削除(論理/物理統合済み)
	@PostMapping("/remove")
	public String removeFromShelf(
			@RequestParam("id") Integer userBookId,
			RedirectAttributes ra) {

		Integer userId = getLoginUserId();
		if (userId == null) {
			ra.addFlashAttribute("errorMessage", "ログイン状態が無効です。もう一度ログインしてください。");
			return "redirect:/login";
		}

		try {
			userBookService.remove(userBookId);
			ra.addFlashAttribute("successMessage", "本棚から削除しました。");
		} catch (RuntimeException e) {
			ra.addFlashAttribute("errorMessage", e.getMessage());
		}

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
