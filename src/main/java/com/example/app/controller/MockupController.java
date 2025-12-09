package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MockupController {

	// ホーム画面
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}

	// ログインフォーム
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 会員登録
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	// お知らせ詳細
	@GetMapping("/notif/detail")
	public String showNotif() {
		return "notif/detail";
	}

	// お知らせ一覧
	@GetMapping("/notif/list")
	public String showNotifList() {
		return "notif/list";
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

	// マイ本棚
	@GetMapping("/book/list")
	public String bookList() {
		return "book/list";
	}

	// ランキング
	@GetMapping("/book/ranking")
	public String bookRanking() {
		return "book/ranking";
	}

	// レコメンド
	@GetMapping("/book/recommend")
	public String bookRecommend() {
		return "book/recommend";
	}

	// 本を探す
	@GetMapping("/book/search")
	public String bookSearch() {
		return "book/search";
	}

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

	// 管理者ログイン
	@GetMapping("/admin/login")
	public String adminLogin() {
		return "admin/login";
	}

	// 管理者用一覧リスト
	@GetMapping("/admin/list")
	public String adminList() {
		return "admin/list";
	}

	// 管理者用書籍登録・編集
	@GetMapping("/admin/book/save")
	public String adminBookSave() {
		return "admin/book/save";
	}

	// 管理者用書籍一覧
	@GetMapping("/admin/book/list")
	public String adminBookList() {
		return "admin/book/list";
	}

	// 管理者用お知らせ登録・編集
	@GetMapping("/admin/notif/save")
	public String adminNotifSave() {
		return "admin/notif/save";
	}

	// 管理者用お知らせ一覧
	@GetMapping("/admin/notif/list")
	public String adminNotifList() {
		return "admin/notif/list";
	}

	// 管理者削除用レビュー一覧
	@GetMapping("/admin/review/list")
	public String adminReviewList() {
		return "admin/review/list";
	}
	
	// 本詳細ページテスト用
	@GetMapping("/book/detail-test")
	public String bookDetailTest() {
		return "book/detail-test";
	}

}
