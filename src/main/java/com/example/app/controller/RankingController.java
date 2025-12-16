package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RankingController {

	private final HttpSession session;

	// ランキング
	@GetMapping("/book/ranking")
	public String bookRanking(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "book/ranking";
	}

}
