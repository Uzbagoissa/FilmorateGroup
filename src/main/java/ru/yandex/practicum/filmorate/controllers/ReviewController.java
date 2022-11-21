package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.Review;
import ru.yandex.practicum.filmorate.services.ReviewService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = "/reviews")
    public List<Review> getReviewsByParams(@RequestParam(required = false) Map<String, Integer> params) {
        log.info("Endpoint request received: 'GET .reviews with params: {}'", params.toString());
        return reviewService.findReviewsByParams(params);
    }

    @PostMapping(value = "/reviews")
    public Review create(@Valid @RequestBody Review review) {
        log.info("Endpoint request received: 'POST reviews'");
        return reviewService.create(review);
    }

    @GetMapping(value = "/reviews/{reviewId}")
    public Review getReviewById(@PathVariable Long reviewId) {
        log.info("Endpoint request received: 'GET reviews/{}'", reviewId);
        return reviewService.getByReviewId(reviewId);
    }

    @PutMapping(value = "/reviews")
    public Review update(@Valid @RequestBody Review review) {
        log.info("Endpoint request received: 'PUT reviews/{}'", review.toString());
        return reviewService.update(review);
    }

    @PutMapping(value = "reviews/{reviewId}/like/{userId}")
    public void addLikeToReview(@PathVariable Integer reviewId, @PathVariable Integer userId) {
        log.info("Endpoint request received: 'PUT reviews/{}/like/{}'", reviewId, userId);
        reviewService.addLikeToReview(reviewId, userId);
    }

    @PutMapping(value = "reviews/{reviewId}/dislike/{userId}")
    public void addDisLikeToReview(@PathVariable Integer reviewId, @PathVariable Integer userId) {
        log.info("Endpoint request received: 'PUT reviews/{}/dislike/{}'", reviewId, userId);
        reviewService.addDisLikeToReview(reviewId, userId);
    }

    @DeleteMapping(value = "reviews/{reviewId}")
    public void removeReview(@PathVariable Long reviewId) {
        log.info("Endpoint request received: 'DELETE reviews/{}'", reviewId);
        reviewService.delete(reviewId);
    }
}
