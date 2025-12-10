package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserLoginForm {
	
	@NotBlank(message="{user.loginId.NotBlank}")
	@Size(max = 30, message="{user.loginId.Size}")
	private String loginId;
	
	@NotBlank(message="{user.loginPass.NotBlank}")
	private String loginPass;

}
