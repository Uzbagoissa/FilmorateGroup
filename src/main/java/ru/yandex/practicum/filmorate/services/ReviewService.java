package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
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
        checkFilmsAndUsersAreExists(review);
        return reviewStorage.create(review);
    }

    public Review update(Review review) {
        checkFilmsAndUsersAreExists(review);
        return reviewStorage.update(review);
    }

    public Review getByReviewId(Long reviewId) {
        return reviewStorage.getById(reviewId);
    }

    private void checkFilmsAndUsersAreExists(Review review) {
        if (!reviewStorage.checkFilmExists(review.getFilmId())) {
            throw new EmptyResultFromDataBaseException("Фильм с идентификатором " + review.getFilmId() + " отсутствует");
        }

        if (!reviewStorage.checkUserExists(review.getUserId())) {
            throw new EmptyResultFromDataBaseException("Пользователь с идентификатором " + review.getFilmId() + " отсутствует");
        }
    }
}
