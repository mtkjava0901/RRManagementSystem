package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.BookGenre;
import com.example.app.mapper.BookGenreMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookGenreServiceImpl implements BookGenreService {

	private final BookGenreMapper mapper;

	// 書籍ジャンル一覧
	@Override
	public List<BookGenre> findAll() {
		return mapper.selectAll();
	}

}
