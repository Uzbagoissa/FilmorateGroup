package ru.yandex.practicum.filmorate.storage.interf;
import ru.yandex.practicum.filmorate.models.Review;

import java.util.List;
import java.util.Map;

public interface ReviewStorage<T extends Review> {
    List<T> getAll();

    List<T> getByParams(Map<String, Integer> params);

    T create(Review review);

    T update(Review review);

    T getById(Long reviewId);

    void addLikeToReview(Integer reviewId, Integer userId);
    void addDisLikeToReview(Integer reviewId, Integer userId);
    void delete(Long reviewId);

    Boolean checkFilmExists(Integer filmId);

    Boolean checkUserExists(Integer userId);

    Boolean checkReviewExists(Integer reviewId);
}
