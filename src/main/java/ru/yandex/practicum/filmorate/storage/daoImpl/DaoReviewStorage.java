package ru.yandex.practicum.filmorate.storage.daoImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exceptions.EmptyResultFromDataBaseException;
import ru.yandex.practicum.filmorate.mapper.ReviewRowMapper;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Review;
import ru.yandex.practicum.filmorate.storage.interf.ReviewStorage;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class DaoReviewStorage implements ReviewStorage<Review> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> getAll() {
        return jdbcTemplate.query(
                Constants.GET_REVIEWS,
                new ReviewRowMapper()
        );
    }

    @Override
    public List<Review> getByParams(Map<String, Integer> params) {
        StringBuilder queryString = new StringBuilder(Constants.GET_REVIEWS_BY_FILM_ID);
        int limit = 10;
        if(!params.isEmpty()) {
            if (params.containsKey("filmId")) {
                queryString.append(" WHERE ")
                        .append("film_id = ")
                        .append(params.get("filmId"));
            }

            queryString.append(" ORDER BY review_id DESC");

            if (params.containsKey("count")) {
                limit = params.get("count");
            }
        } else {
            queryString.append(" ORDER BY review_id DESC");
        }

        queryString.append(" LIMIT ").append(limit);

        return jdbcTemplate.query(
                queryString.toString(),
                new ReviewRowMapper()
        );
    }

    @Override
    public Review create(Review review) {
        createOrUpdate(
                Constants.CREATE_REVIEW,
                new Object[]{
                        review.getUserId(),
                        review.getFilmId(),
                        review.getContent(),
                        review.getIsPositive(),
                        review.getIsPositive() ? 1 : 0
                }
        );

        return jdbcTemplate.query(
                        Constants.GET_LAST_REVIEW,
                        new ReviewRowMapper()
                )
                .stream()
                .findAny()
                .orElseThrow(() -> new EmptyResultFromDataBaseException("Ничего не найдено!"));
    }

    @Override
    public Review update(Review review) {
        createOrUpdate(
                Constants.UPDATE_REVIEW,
                new Object[]{
                        review.getUserId(),
                        review.getFilmId(),
                        review.getContent(),
                        review.getIsPositive(),
                        review.getUseful(),
                        review.getReviewId()
                }
        );

        return jdbcTemplate.query(
                        Constants.GET_LAST_REVIEW_AFTER_UPDATE,
                        new ReviewRowMapper(),
                        review.getReviewId()
                )
                .stream()
                .findAny()
                .orElseThrow(() -> new EmptyResultFromDataBaseException("Ничего не найдено!"));
    }

    @Override
    public Review getById(Long reviewId) {
        return jdbcTemplate.query(
            Constants.GET_REVIEWS_BY_ID,
            new ReviewRowMapper(),
            reviewId
        )
                .stream()
                .findAny()
                .orElseThrow(() -> new EmptyResultFromDataBaseException("Отзыв не найден"));
    }

    @Override
    public Boolean checkFilmExists(Integer filmId) {
        Integer filmsCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM films WHERE id = ?",
                Integer.class,
                filmId
        );

        return filmsCount != null && filmsCount > 0;
    }

    @Override
    public Boolean checkUserExists(Integer userId) {
        Integer usersCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE id = ?",
                Integer.class,
                userId
        );

        return usersCount != null && usersCount > 0;
    }

    private void createOrUpdate(String query, Object[] obj) {
        jdbcTemplate.update(
                query,
                obj

        );
    }
}
