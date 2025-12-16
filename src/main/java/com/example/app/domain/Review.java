package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Review {

	// レビュー格納用エンティティ

	private Integer id; // PK

	private Integer userId; // FK ユーザーID

	private Integer bookId; // FK 書籍ID

	private Integer rating; // 星評価(1～5)

	private String comment; // コメント

	private LocalDateTime createdAt; // 登録日時

	private LocalDateTime updatedAt; // 更新日時

}
