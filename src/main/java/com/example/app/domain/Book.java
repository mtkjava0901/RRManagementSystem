package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Book {

	private Integer id; // PK

	private String title; // 書籍名

	private String author; // 著者

	private String publisher; // 出版社

	private LocalDate publisherAt; //発行日

	private BookGenre bookGenre; // ジャンル

	private LocalDateTime createdAt; // 登録日時(管理用)

	private LocalDateTime updatedAt; // 更新日時(管理用)

	private String status; // 状態

	private Boolean manual; // 0=ユーザー追加(物理削除用) 1=管理者追加

}
