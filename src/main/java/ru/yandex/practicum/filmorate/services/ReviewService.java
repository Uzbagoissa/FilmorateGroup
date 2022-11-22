package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EmptyResultFromDataBaseException;
import ru.yandex.practicum.filmorate.models.Review;
import ru.yandex.practicum.filmorate.storage.interf.EventStorage;
import ru.yandex.practicum.filmorate.storage.interf.ReviewStorage;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Qualifier("daoReviewStorage")
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewStorage<Review> reviewStorage;

    private final EventStorage eventStorage;

    public List<Review> getAll() {
        return reviewStorage.getAll();
    }

    public List<Review> findReviewsByParams(Map<String, Integer> params) {
        return reviewStorage.getByParams(params);
    }

    public Review create(Review review) {
        if (!reviewStorage.checkFilmExists(review.getFilmId())) {
            throw new EmptyResultFromDataBaseException("Фильм с идентификатором " + review.getFilmId() + " отсутствует");
        }

        if (!reviewStorage.checkUserExists(review.getUserId())) {
            throw new EmptyResultFromDataBaseException("Пользователь с идентификатором " + review.getUserId() + " отсутствует");
        }

        Map<String, Object> params = eventStorage.makeEvent(
                (long)review.getUserId(),
                review.getFilmId(),
                "review",
                "add"
        );
        eventStorage.save(params);
        return reviewStorage.create(review);
    }

    public Review update(Review review) {
        if (!reviewStorage.checkFilmExists(review.getFilmId())) {
            throw new EmptyResultFromDataBaseException("Фильм с идентификатором " + review.getFilmId() + " отсутствует");
        }
        if (!reviewStorage.checkUserExists(review.getUserId())) {
            throw new EmptyResultFromDataBaseException("Пользователь с идентификатором " + review.getUserId() + " отсутствует");
        }
        Map<String, Object> params = eventStorage.makeEvent(
                1L,
                1,
                "review",
                "update"
        );
        eventStorage.save(params);
        return reviewStorage.update(review);
    }

    public Review getByReviewId(Long reviewId) {
        return reviewStorage.getById(reviewId);
    }

    public void addLikeToReview(Integer reviewId, Integer userId) {
        if (!reviewStorage.checkUserExists(userId)) {
            throw new EmptyResultFromDataBaseException("Пользователь с идентификатором " + userId + " отсутствует");
        }

        if (!reviewStorage.checkReviewExists(reviewId)) {
            throw new EmptyResultFromDataBaseException("Отзыв с идентификатором " + reviewId + " отсутствует");
        }
        reviewStorage.addLikeToReview(reviewId, userId);
    }

    public void addDisLikeToReview(Integer reviewId, Integer userId) {
        if (!reviewStorage.checkUserExists(userId)) {
            throw new EmptyResultFromDataBaseException("Пользователь с идентификатором " + userId + " отсутствует");
        }

        if (!reviewStorage.checkReviewExists(reviewId)) {
            throw new EmptyResultFromDataBaseException("Отзыв с идентификатором " + reviewId + " отсутствует");
        }
        reviewStorage.addDisLikeToReview(reviewId, userId);
    }

    public void delete(Long reviewId) {
        if (!reviewStorage.checkReviewExists(Math.toIntExact(reviewId))) {
            throw new EmptyResultFromDataBaseException("Отзыв с идентификатором " + reviewId + " отсутствует");
        }
        Map<String, Object> params = eventStorage.makeEvent(
                1L,
                Math.toIntExact(reviewId),
                "review",
                "remove"
        );
        eventStorage.save(params);
        reviewStorage.delete(reviewId);
    }
}
