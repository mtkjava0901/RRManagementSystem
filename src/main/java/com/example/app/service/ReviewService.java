package com.example.app.service;

import java.util.List;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;
import com.example.app.domain.UserBook;

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

	// ユーザー登録か否か判定(userId,bookId)
	boolean isReviewable(Integer userId, Integer userBookId);
	
	// ユーザー登録か否か判定(UserBook)
	boolean isReviewable(UserBook userBook);
	
	// レビュー可能なUserBookを見つける
	UserBook findReviewableUserBook(Integer userId, Integer bookId);
	
	// UserBookIdでレビュー可能なユーザーブックを見つける
	UserBook findReviewableUserBookByUserBookId(Integer userId, Integer userBookId);

}
