package com.ramchaik.reviewms.review;

import com.ramchaik.reviewms.review.dto.ReviewMessage;
import com.ramchaik.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReview(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getReviewsForCompany(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/averageRating")
    public Double getAverageRatingForCompany(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getReviewsForCompany(companyId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestBody Review review
    ) {
        reviewService.createReview(review);
        reviewMessageProducer.sendMessage(review);
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Review review
    ) {
        Review updatedReview = reviewService.updateReview(reviewId, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId
    ) {
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if (!isDeleted) {
            throw new RuntimeException("Failed to delete");
        }
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
