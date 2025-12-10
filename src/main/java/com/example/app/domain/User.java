package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	
	private Integer id; // ユーザー主キー
	
	private String loginId; // ログインID(Email)
	
	private String loginPass; // ログインパス(BCrypt使用)
	
	private String name; // ニックネーム
	
	private LocalDateTime registeredAt; // 登録日時
	
	private LocalDateTime updatedAt; // 更新日時
	
	private String status; // 状態('ACT'or'DEL')

}
