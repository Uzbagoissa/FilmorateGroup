package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Film;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.interf.EventStorage;
import ru.yandex.practicum.filmorate.storage.interf.FilmStorage;

import java.util.*;


@Service
@Slf4j
@Qualifier("daoFilmStorage")
@RequiredArgsConstructor
public class FilmService {
    private final UserService userService;
    private final FilmStorage filmStorage;

    private final EventStorage eventStorage;

    public List<Film> getFilms(){
        return filmStorage.getFilms();
    }
    public Film getFilmById(Integer filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film addFilm(Film film){
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film){
        return filmStorage.updateFilm(film);
    }
    public void removeFilm(Film film){
        filmStorage.removeFilm(film);
    }
    public Film addLikeFromUserById(Integer filmId, Integer userId){
        Film film = filmStorage.getFilmById(filmId);
        User user = userService.getUserById(userId);

        Map<String, Object> params = eventStorage.makeEvent(
                (long)userId,
                filmId,
                "like",
                "add"
        );
        eventStorage.save(params);

        return filmStorage.addLikeFromUserById(film.getId(), user.getId());
    }
    public Film removeLikeFromUserById(Integer filmId, Integer userId){
        Film film = filmStorage.getFilmById(filmId);
        User user = userService.getUserById(userId);

        Map<String, Object> params = eventStorage.makeEvent(
                (long)userId,
                filmId,
                "like",
                "remove"
        );
        eventStorage.save(params);

        return filmStorage.removeLikeFromUserById(film.getId(), user.getId());
    }
    public List<Film> getMostPopularFilmByCountLikes(Integer count){
        return filmStorage.getMostPopularFilmByCountLikes(count);
    }
    public UserService getUserService() {
        return userService;
    }

}