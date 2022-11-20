package ru.yandex.practicum.filmorate;

public class Constants {
    // REVIEWS
    public static final String GET_REVIEWS = "SELECT * FROM reviews ORDER BY review_id DESC";
    public static final String GET_REVIEWS_BY_FILM_ID = "SELECT * FROM reviews";
    public static final String CREATE_REVIEW = "INSERT INTO reviews SET user_id = ?, film_id = ?, content = ?, is_positive = ?, useful = ?";
    public static final String UPDATE_REVIEW = "UPDATE reviews SET user_id = ?, film_id = ?, content = ?, is_positive = ?, useful = ? WHERE review_id = ?";
    public static final String GET_LAST_REVIEW = "SELECT * FROM reviews ORDER BY review_id DESC LIMIT 1";
    public static final String GET_LAST_REVIEW_AFTER_UPDATE = "SELECT * FROM reviews WHERE review_id = ?";
    public static final String GET_REVIEWS_BY_ID = "SELECT * FROM reviews WHERE review_id = ?";
}
