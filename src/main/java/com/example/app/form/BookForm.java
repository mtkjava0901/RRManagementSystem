package com.example.app.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class BookForm {

	// 画面入力、バリデーション、一時データ置き場
	// (domain破綻防止用クラス)

	@NotBlank(message = "{book.title.NotBlank}")
	@Size(max = 100, message = "{book.title.Size}")
	private String title;

	@NotBlank(message = "{book.author.NotBlank}")
	@Size(max = 60, message = "{book.author.Size}")
	private String author;

	@Size(max = 60, message = "{book.publisher.Size}")
	private String publisher;

	@Past(message = "{book.publishedAt.Past}")
	private LocalDate publishedAt;

	@NotNull(message = "{book.genreId.NotNull}")
	private Integer genreId;

}
