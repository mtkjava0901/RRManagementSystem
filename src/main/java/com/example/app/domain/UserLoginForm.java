package com.example.app.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserLoginForm {
	
	@NotBlank(message="{user.loginId.NotBlank}")
	@Email
	private String loginId;
	
	@NotBlank(message="{user.loginPass.NotBlank}")
	private String loginPass;

}
