package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Book;
import com.example.app.domain.BookGenre;
import com.example.app.service.BookGenreService;
import com.example.app.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;
	private final BookGenreService bookGenreService;

	// 本を探す
	@GetMapping("/book/search")
	public String searchBook(
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "genreId", required = false, defaultValue = "0") Integer genreId,
	        @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
	        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
	        @RequestParam(value = "doSearch", required = false) String doSearch,
	        Model model) {

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
        model.addAttribute("genreId", genreId);  // UI の選択は保持
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


	// 本を追加する(マニュアル)
	@GetMapping("/book/add")
	public String addBook() {
		return "book/add";
	}

	// 書籍詳細
	@GetMapping("/book/detail")
	public String bookDetail() {
		return "book/detail";
	}

	// 書籍詳細(after/確認用)
	@GetMapping("/book/detail-after")
	public String bookDetailAfter() {
		return "book/detail-after";
	}

	// 本詳細ページテスト用
	@GetMapping("/book/detail-test")
	public String bookDetailTest() {
		return "book/detail-test";
	}

	// マイ本棚
	@GetMapping("/book/list")
	public String bookList() {
		return "book/list";
	}

}
