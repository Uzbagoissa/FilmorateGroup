package ru.yandex.practicum.filmorate.storage.interf;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);
    void removeFilm(int id);
    Film updateFilm(Film film);
    List<Film> getFilms();
    Film getFilmById(Integer filmId);

    Film addLikeFromUserById(Integer filmId, Integer userId);

    Film removeLikeFromUserById(Integer filmId, Integer userId);

    List<Film> getMostPopularFilmByCountLikes(Integer count);
}
