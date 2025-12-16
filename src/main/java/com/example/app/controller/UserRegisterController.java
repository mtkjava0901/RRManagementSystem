package com.example.app.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.User;
import com.example.app.domain.UserRegisterForm;
import com.example.app.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserRegisterController {

	private final UserService userService;

	// 会員登録
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("userRegisterForm", new UserRegisterForm());
		return "register";
	}

	@PostMapping("/register")
	public String register(
			@Validated @ModelAttribute("userRegisterForm") UserRegisterForm form,
			BindingResult result,
			RedirectAttributes ra) {

		if (result.hasErrors()) {
			return "register";
		}

		// パスワード一致チェック
		if (!form.getLoginPass().equals(form.getConfirmPass())) {
			result.rejectValue("confirmPass", "register.confirmPass.mismatch", "パスワードが一致しません");
			return "register";
		}

		// 重複チェック
		if (userService.existsByLoginId(form.getLoginId())) {
			result.rejectValue("loginId", "register.loginId.duplicate", "このメールアドレスは既に使用されています");
			return "register";
		}

		// User に変換して登録
		User user = new User();
		user.setLoginId(form.getLoginId());
		user.setLoginPass(BCrypt.hashpw(form.getLoginPass(), BCrypt.gensalt()));
		user.setName(form.getName());

		// ユーザー作成処理
		userService.addUser(user);

		ra.addFlashAttribute("successMessage", "会員登録が完了しました");
		return "redirect:/login";
	}

}
