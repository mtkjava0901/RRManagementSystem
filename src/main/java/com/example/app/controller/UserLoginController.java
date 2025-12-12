package com.example.app.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.User;
import com.example.app.domain.UserLoginForm;
import com.example.app.service.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserLoginController {
	
	private final AuthService service;
	private final HttpSession session;
	
	// ログインフォーム
	@GetMapping("/login")
	public String login(Model model) {

		if (!model.containsAttribute("loginForm")) {
			model.addAttribute("loginForm", new UserLoginForm());
		}

		if (session.getAttribute("admin") != null) {
			session.removeAttribute("admin");
		}

		if (session.getAttribute("user") != null) {
			return "redirect:/home";
		}
		return "login";
	}
	
	@PostMapping("/login")
	public String userLogin(
			@Valid @ModelAttribute("loginForm") UserLoginForm form,
			Errors errors,
			Model model) {

		// 認証失敗時にはerrorsがセットされる
		User user = service.authenticateUser(form.getLoginId(), form.getLoginPass(), errors);

		if (errors.hasErrors()) {
			return "login";
		}

		// 成功したら逆側のセッションを破棄
		session.removeAttribute("admin");
		session.setAttribute("user", user);
		session.setAttribute("loginUserId", user.getId());
		return "redirect:/home";
	}
	
	// ログアウト
	@GetMapping("/logout")
	public String userLogout(RedirectAttributes rd) {
		// セッションを破棄してトップページへ遷移
		session.invalidate();
		rd.addFlashAttribute("loginMessage", "ログアウトしました。");
		return "redirect:/login";
	}
	


}
