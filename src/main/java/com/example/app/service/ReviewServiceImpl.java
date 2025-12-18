package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Review;
import com.example.app.domain.ReviewForm;
import com.example.app.domain.UserBook;
import com.example.app.enums.BookSource;
import com.example.app.mapper.ReviewMapper;
import com.example.app.mapper.UserBookMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;
	private final UserBookMapper userBookMapper;

	// レビュー投稿
	@Override
	public Review addReview(Integer userId, ReviewForm form) {

		Integer userBookId = form.getUserBookId();

		// UserBook 取得
		UserBook userBook = userBookMapper.selectActiveById(userBookId);

		// null/ユーザーが違う場合
		if (userBook == null || !userBook.getUserId().equals(userId)) {
			throw new IllegalArgumentException("不正な操作です");
		}

		// 手動登録はレビュー不可
		if (userBook.getSource() == BookSource.MANUAL) {
			throw new IllegalArgumentException("手動登録した書籍はレビューできません");
		}

		// 既存レビュー確認（bookId で判定）
		if (existsUserReview(userId, userBook.getBookId())) {
			throw new IllegalArgumentException("この書籍にはすでにレビュー済みです。");
		}

		Review review = new Review();
		review.setUserId(userId);
		review.setBookId(form.getUserBookId());
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

	// ユーザー登録か否か判定(橋渡しメソッド)
	@Override
	public boolean isReviewable(Integer userId, Integer userBookId) {
		UserBook userBook = userBookMapper.selectActiveById(userBookId);
		return isReviewable(userBook);
	}

	// ユーザー登録か否か判定
	@Override
	public boolean isReviewable(UserBook userBook) {

		if (userBook == null) {
			// 本棚に存在しないor削除済み
			return false;
		}
		return userBook.getSource() != BookSource.MANUAL;

	}

	// レビュー可能なUserBookを見つける(この書籍に対して、レビュー投稿画面を出していいか？/bookId起点)
	@Override
	public UserBook findReviewableUserBook(Integer userId, Integer bookId) {

		UserBook ub = userBookMapper.selectActive(userId, bookId);

		if (ub == null) {
			return null;
		}

		if (ub.getSource() == BookSource.MANUAL) {
			return null;
		}

		return ub;
	}

	// UserBookIdでレビュー可能なユーザーブックを見つける(「POST(レビュー登録)」用/userBookId起点)
	@Override
	public UserBook findReviewableUserBookByUserBookId(Integer userId, Integer userBookId) {

		UserBook ub = userBookMapper.selectActiveById(userBookId);

		if (ub == null || !ub.getUserId().equals(userId)) {
			return null;
		}

		if (ub.getSource() == BookSource.MANUAL) {
			return null;
		}

		return ub;
	}

}
