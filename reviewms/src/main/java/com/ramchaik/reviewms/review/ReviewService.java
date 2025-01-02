package com.ramchaik.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsForCompany(Long companyId);
    Review getReview(Long reviewId);
    void createReview(Review review);
    Review updateReview(Long reviewId, Review review);
    boolean deleteReview(Long reviewId);
}
