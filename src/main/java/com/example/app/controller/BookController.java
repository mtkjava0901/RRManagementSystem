package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class BookController {
	
	// ホーム画面
	@GetMapping("/home")
	public String showHome() {
		return "home";
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

	// 本を探す
	@GetMapping("/book/search")
	public String bookSearch() {
		return "book/search";
	}


}
