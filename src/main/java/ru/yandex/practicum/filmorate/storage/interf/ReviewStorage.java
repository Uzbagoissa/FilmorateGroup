package ru.yandex.practicum.filmorate.storage.interf;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Review;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReviewStorage<T extends Review> {
    List<T> getAll();

    List<T> getByParams(Map<String, Integer> params);

    T create(Review review);

    T update(Review review);

    T getById(Long reviewId);

    Boolean checkFilmExists(Integer filmId);

    Boolean checkUserExists(Integer userId);
}
