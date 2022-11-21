package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EmptyResultFromDataBaseException;
import ru.yandex.practicum.filmorate.models.Review;
import ru.yandex.practicum.filmorate.storage.interf.ReviewStorage;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Qualifier("daoReviewStorage")
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewStorage<Review> reviewStorage;

    public List<Review> getAll() {
        return reviewStorage.getAll();
    }

    public List<Review> findReviewsByParams(Map<String, Integer> params) {
        return reviewStorage.getByParams(params);
    }

    public Review create(Review review) {
        return reviewStorage.create(review);
    }

    public Review update(Review review) {
        return reviewStorage.update(review);
    }

    public Review getByReviewId(Long reviewId) {
        return reviewStorage.getById(reviewId);
    }

    public void addLikeToReview(Integer reviewId, Integer userId) {
        reviewStorage.addLikeToReview(reviewId, userId);
    }

    public void addDisLikeToReview(Integer reviewId, Integer userId) {
        reviewStorage.addDisLikeToReview(reviewId, userId);
    }

    public void delete(Long reviewId) {
        reviewStorage.delete(reviewId);
    }
}
