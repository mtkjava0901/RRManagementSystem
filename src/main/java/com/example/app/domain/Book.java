package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Book {

	// 書籍

	private Integer id; // PK

	private String title; // 書籍名

	private String author; // 著者

	private String publisher; // 出版社

	private LocalDate publisherAt; //発行日

	private BookGenre bookGenre; // ジャンル

	private LocalDateTime createdAt; // 登録日時(管理用)

	private LocalDateTime updatedAt; // 更新日時(管理用)

	private String status; // 状態(ACTorDEL)

	private Boolean manual; // true(0)=ユーザー手動登録書籍 false(1)=管理者/API由来の書籍

}
