package com.example.app.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginatedResult<T> {
	
	// ユーザー本棚専用のページング用クラス
	
	private List<T> items;     // 表示する要素
  private int totalCount;     // 総件数
  private int totalPages;     // 総ページ数
  private int currentPage;    // 現在のページ番号

}
