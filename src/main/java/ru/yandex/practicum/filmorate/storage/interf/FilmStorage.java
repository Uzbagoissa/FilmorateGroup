package ru.yandex.practicum.filmorate.storage.interf;

import ru.yandex.practicum.filmorate.models.Film;

import java.time.Year;
import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);
    void removeFilm(Film film);
    Film updateFilm(Film film);
    List<Film> getFilms();
    Film getFilmById(Integer filmId);

    Film addLikeFromUserById(Integer filmId, Integer userId);

    Film removeLikeFromUserById(Integer filmId, Integer userId);

    List<Film> findCommon(int userId, int friendsId);

    List<Film> getMostPopularFilmByCountLikes(Integer count, Integer genreId, Year year);

    List<Film> getSortedFilmByDirector(Integer id, String sortBy);
}
