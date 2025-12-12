package com.example.app.domain;

import java.time.LocalDateTime;

import com.example.app.enums.ReadingStatus;

import lombok.Data;

@Data
public class UserBook {
	
	private Integer id; // PK
	
  private Integer userId;   // FK ユーザーID
  
  private Integer bookId;   // FK 書籍ID
  
  private Integer rating; // 星評価(1～5)
  
  private ReadingStatus status;  // ENUM型 読書ステータス('読みたい', '未読', '読書中', '既読', '未指定')
  
  private String memo; // メモ
  
  private LocalDateTime createdAt; // 登録日時
  
  private LocalDateTime updatedAt; // 更新一時
  
  private Boolean isDeleted;     // 0 = false(消してない=本棚に存在), 1 = true(消した=本棚に存在していない)
}


