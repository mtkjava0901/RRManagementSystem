package com.example.app.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReviewForm {
	
	// レビューフォームのバリデーション用ドメイン
		
	@NotNull(message="{review.userBookId.NotNull}")
	private Integer userBookId;
	
	private Integer bookId;
	
	@Min(1)
	@Max(5)
	private Integer rating;
	
	@NotBlank(message="{review.comment.NotBlank}")
	private String comment;

}
