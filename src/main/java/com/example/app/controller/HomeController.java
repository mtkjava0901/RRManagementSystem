package com.example.app.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.domain.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final HttpSession session;
	// private final AuthService authService;
	// private final UserService userService;

	// /homeへのリダイレクト	
	@GetMapping("/")
	public String redirectToHome() {
		// sessionにstudentがいなければログインへ
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		// ログインしていたら/rentalへ
		return "redirect:/home";
	}

	// HOME
	@GetMapping("/home")
	public String showHome(Model model) {

		// セッションからユーザー取得
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "home";
	}

}
