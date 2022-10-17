package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.ValidationFilmByIdException;
import ru.yandex.practicum.filmorate.models.Film;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FilmService {
    private final UserService userService;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(UserService userService){
        this.userService = userService;
        this.filmStorage = new InMemoryFilmStorage();
    }

    public List<Film> getFilms(){
        return new ArrayList<>(filmStorage.getFilms().values());
    }
    public Film getFilm(Integer filmId) {
        return filmStorage.getFilm(filmId);
    }

    public Film addFilm(Film film){
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film){
        return filmStorage.updateFilm(film);
    }
    public Film removeFilm(Film film){
        return filmStorage.removeFilm(film);
    }

    public Film addLikeFromUserById(Integer filmId, Integer userId){
        User user = userService.getUserById(userId);

        validFilmById(filmId);
        Film film = filmStorage.getFilms().get(filmId);
        film.getLikes().add(user.getId());

        filmStorage.getFilms().put(film.getId(), film);

        log.info("Пользователь с id: {} поставил like фильму: {}", user.getId(), film.getName());
        return film;
    }

    public Film removeLikeFromUserById(Integer filmId, Integer userId){
        User user = userService.getUserById(userId);
        validFilmById(filmId);

        Film film = filmStorage.getFilms().get(filmId);
        film.getLikes().remove(user.getId());

        filmStorage.getFilms().put(film.getId(), film);

        log.info("Пользователь с id: {} убрал like фильму: {}", user.getId(), film.getName());
        return film;
    }

    public List<Film> getMostPopularFilmByCountLikes(Integer count){
        return filmStorage.getFilms().values().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }
    private int compare(Film p0, Film p1){
        return p1.getLikes().size() - p0.getLikes().size();
    }

    private void validFilmById(Integer filmId){
        if(!filmStorage.getFilms().containsKey(filmId)){
            throw new ValidationException("Фильма с id: " + filmId + " нет в базе");
        }
    }

    public UserService getUserService() {
        return userService;
    }
}
