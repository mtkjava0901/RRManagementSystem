package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class NotifController {
	
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

}
