package com.example.app.service;

import java.util.List;

import com.example.app.domain.BookGenre;

public interface BookGenreService {
	
	// 書籍ジャンル一覧
	List<BookGenre> findAll();

}
