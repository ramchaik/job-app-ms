package com.ramchaik.reviewms.review.impl;

import com.ramchaik.reviewms.review.Review;
import com.ramchaik.reviewms.review.ReviewRepository;
import com.ramchaik.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getReviewsForCompany(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public void createReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, Review review) {
        Review reviewOnDb = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Resource not found"));

        reviewOnDb.setTitle(review.getTitle());
        reviewOnDb.setDescription(review.getDescription());
        reviewOnDb.setCompanyId(review.getCompanyId());
        reviewOnDb.setRating(review.getRating());

        reviewRepository.save(reviewOnDb);

        return reviewOnDb;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        try {
            if (reviewRepository.existsById(reviewId)) {
                Review review = reviewRepository.findById(reviewId).orElse(null);
                assert review != null;

                reviewRepository.delete(review);
                return true;
            }
            return false;
        } catch (Exception e) {
           return false;
        }
    }
}
