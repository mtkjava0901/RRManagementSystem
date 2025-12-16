package com.example.app.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRegisterForm {
	
	// ログインフォーム用
	
	@NotBlank(message="{register.loginId.NotBlank}")
	@Email(message="{register.loginId.Email}", regexp=".+@.+")
	// @Email(message="{register.loginId.Email}") // 正規用バリデーション(RFC準拠/発表用では使わない)
  private String loginId;

  @NotBlank(message = "{register.loginPass.NotBlank}")
  private String loginPass;

  @NotBlank(message = "{register.confirmPass.NotBlank}")
  private String confirmPass;

  @NotBlank(message = "{register.name.NotBlank}")
  @Size(max = 30, message = "{register.name.Size}")
  private String name; 
	
}