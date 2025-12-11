package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AdminLoginForm {
	
	@NotBlank(message="{admin.loginId.NotBlank}")
	private String loginId;
	
	@NotBlank(message="{admin.loginPass.NotBlank}")	
	private String loginPass;

}
