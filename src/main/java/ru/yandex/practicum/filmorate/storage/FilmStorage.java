package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.Map;

public interface FilmStorage {

    Film addFilm(Film film);
    Film removeFilm(Film film);
    Film updateFilm(Film film);
    Map<Integer, Film> getFilms();
    Film getFilm(Integer filmId);
}
