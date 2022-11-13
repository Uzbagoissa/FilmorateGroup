package ru.yandex.practicum.filmorate.storage.interf;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);
    void removeFilm(Film film);
    Film updateFilm(Film film);
    List<Film> getFilms();
    Film getOrValidFilmById(Integer filmId);

    Film addLikeFromUserById(Integer filmId, Integer userId);

    Film removeLikeFromUserById(Integer filmId, Integer userId);

    List<Film> getMostPopularFilmByCountLikes(Integer count);

    List<Genre> getAllGenre();

    Genre getGenreById(Integer id);

    List<Mpa> getAllMpa();

    Mpa getMpaById(Integer id);
}
