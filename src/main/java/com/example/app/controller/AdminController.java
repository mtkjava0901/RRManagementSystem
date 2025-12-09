package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {
	
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

}
