package com.example.app.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Book;
import com.example.app.domain.BookGenre;
import com.example.app.form.BookForm;
import com.example.app.service.BookGenreService;
import com.example.app.service.BookService;
import com.example.app.service.UserBookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;
	private final BookGenreService bookGenreService;
	private final UserBookService userBookService;
	private final HttpSession session;

	// ログイン中はユーザーIDを取得
	private Integer getLoginUserId() {
		return (Integer) session.getAttribute("loginUserId");
	}

	// 本を探す
	@GetMapping("/search")
	public String searchBook(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "0") Integer genreId,
			@RequestParam(required = false, defaultValue = "id") String sort,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false) String doSearch,
			Model model) {

		// Integer userId = getLoginUserId();

		final int PAGE_SIZE = 10;

		// 判定
		boolean isSearchTriggered = "1".equals(doSearch);
		boolean hasKeyword = (keyword != null && !keyword.isBlank());

		// 初回アクセス(doSearchなし&keywordなし)
		if (!isSearchTriggered && keyword == null) {
			List<BookGenre> genres = bookGenreService.findAll();

			model.addAttribute("bookList", List.of());
			model.addAttribute("genres", genres);
			model.addAttribute("keyword", "");
			model.addAttribute("genreId", 0);
			model.addAttribute("sort", sort);
			model.addAttribute("page", 1);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalCount", 0);

			return "book/search";
		}

		// キーワードなし→検索しない
		if (isSearchTriggered && !hasKeyword) {
			List<BookGenre> genres = bookGenreService.findAll();

			model.addAttribute("bookList", List.of());
			model.addAttribute("genres", genres);
			model.addAttribute("keyword", "");
			model.addAttribute("genreId", genreId); // UI の選択は保持
			model.addAttribute("sort", sort);
			model.addAttribute("page", 1);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalCount", 0);

			return "book/search";
		}

		// キーワードあり→検索実行
		int totalCount = bookService.countBooks(keyword, genreId);
		List<Book> bookList = bookService.searchBooks(keyword, genreId, sort, page, PAGE_SIZE);
		List<BookGenre> genres = bookGenreService.findAll();

		// ページ数計算
		int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

		model.addAttribute("bookList", bookList);
		model.addAttribute("genres", genres);
		model.addAttribute("keyword", keyword);
		model.addAttribute("genreId", genreId);
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalCount", totalCount);

		return "book/search";
	}

	// 本を追加する(ユーザー登録)
	@GetMapping("/add")
	public String addBook(Model model) {
		model.addAttribute("bookForm", new BookForm());
		model.addAttribute("genres", bookGenreService.findAll());
		return "book/add";
	}

	@PostMapping("/add")
	public String addManualBook(
			@Validated @ModelAttribute("bookForm") BookForm form,
			BindingResult result,
			RedirectAttributes ra,
			Model model) {

		Integer userId = getLoginUserId();
		if (userId == null) {
			return "redirect:/login";
		}

		if (result.hasErrors()) {
			model.addAttribute("genres", bookGenreService.findAll());
			return "book/add";
		}

		// 書籍を作る
		Integer bookId = bookService.createManualBook(form);

		// 作った書籍を本棚へ入れる
		userBookService.add(userId, bookId);

		ra.addFlashAttribute(
				"successMessage",
				"新規作成書籍を本棚に追加しました");

		return "redirect:/book/search";
	}

}
