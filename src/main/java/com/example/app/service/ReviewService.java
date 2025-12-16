package com.example.app.service;

import java.util.List;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;

public interface ReviewService {

	// レビュー投稿
	Review addReview(Integer userId, ReviewForm form);

	// 書籍単位のレビュー一覧
	List<Review> getReviewsByBook(Integer bookId);

	// レビュー1件取得
	Review getReview(Integer reviewId);

	// 全レビュー取得（新着順）
	List<Review> getAllReviews();

	// ユーザーが書籍に既にレビューしているか
	boolean existsUserReview(Integer userId, Integer bookId);

	// ユーザー登録か否か判定
	boolean isReviewable(Integer userId, Integer bookId);

}
