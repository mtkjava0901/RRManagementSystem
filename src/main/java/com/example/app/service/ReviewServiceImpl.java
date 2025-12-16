package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;
import com.example.app.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;

	// レビュー投稿
	@Override
	public Review addReview(Integer userId, ReviewForm form) {

		// 既存レビューがあるか確認
		if (existsUserReview(userId, form.getBookId())) {
			throw new IllegalArgumentException("この書籍にはすでにレビュー済みです。");
		}

		Review review = new Review();
		review.setUserId(userId);
		review.setBookId(form.getBookId());
		review.setRating(form.getRating());
		review.setComment(form.getComment());

		reviewMapper.insertReview(review);
		return review;
	}

	// 書籍単位のレビュー一覧
	@Override
	public List<Review> getReviewsByBook(Integer bookId) {
		return reviewMapper.selectReviewsByBookId(bookId);
	}

	// レビュー1件取得
	@Override
	public Review getReview(Integer reviewId) {
		return reviewMapper.selectReviewById(reviewId);
	}

	// 全レビュー取得（新着順）
	@Override
	public List<Review> getAllReviews() {
		return reviewMapper.selectAllReviews();
	}

	// ユーザーが書籍に既にレビューしているか
	@Override
	public boolean existsUserReview(Integer userId, Integer bookId) {
		return reviewMapper.selectByUserAndBook(userId, bookId) != null;
	}

}
