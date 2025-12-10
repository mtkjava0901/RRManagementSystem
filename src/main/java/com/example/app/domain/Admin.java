package com.example.app.domain;

import lombok.Data;

@Data
public class Admin {
	
	private Integer id; // 管理者PK
	
	private String loginId; // ID
	
	private String loginPass; // PASS

}
