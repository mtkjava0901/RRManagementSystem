package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Review;

@Mapper
public interface ReviewMapper {
	
	// 1件レビュー追加
	int insertReview(Review review);

	// 書籍単位のレビュー一覧取得（新着順）
	List<Review> selectReviewsByBookId(@Param("bookId") Integer bookId);

	// 書籍単位のレビュー1件取得
	Review selectReviewById(@Param("reviewId") Integer reviewId);

	// 全レビュー取得（新着順）
	List<Review> selectAllReviews();

	// ユーザーが書籍に既にレビューしているかチェック
	Review selectByUserAndBook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
