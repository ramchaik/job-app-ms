package com.ramchaik.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReview(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getReviewsForCompany(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestBody Review review
    ) {
        reviewService.createReview(review);
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
