package com.example.app.domain;

import java.time.LocalDateTime;

import com.example.app.enums.BookSource;
import com.example.app.enums.ReadingStatus;

import lombok.Data;

@Data
public class UserBook {

	// 本棚の存在or非存在の責任を持つエンティティ

	private Integer id; // PK

	private Integer userId; // FK ユーザーID

	private Integer bookId; // FK 書籍ID

	private Integer rating; // 星評価(1～5)

	private ReadingStatus status; // ENUM型 読書ステータス('読みたい', '未読', '読書中', '既読', '未指定')

	private String memo; // メモ

	private LocalDateTime createdAt; // 登録日時

	private LocalDateTime updatedAt; // 更新日時

	private Boolean isDeleted; // 本棚から外されたか 0=false(本棚に存在), 1=true(論理削除済)

	private Book book; // JOINでbook情報を持たせる場合(一応用意)

	private BookSource source; // 登録元(ENUM型)
}
