package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.services.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    @Getter
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable("id") Integer filmId) {
        return filmService.getFilmById(filmId);
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/films")
    public void removeFilm(@RequestBody Film film) {
        filmService.removeFilm(film);
    }


    @PutMapping("/films/{id}/like/{userId}")
    public Film addLikeFromUserById(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        return filmService.addLikeFromUserById(filmId, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film removeLikeFromUserById(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        return filmService.removeLikeFromUserById(filmId, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getMostPopularFilmByCountLikes(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        if (count < 0) {
            throw new IncorrectParameterException("count");
        }

        return filmService.getMostPopularFilmByCountLikes(count);
    }
    @GetMapping("/genres")
    public List<Genre> getAllGenre(){
        return filmService.getAllGenre();
    }
    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable("id") Integer id){
        return filmService.getGenreById(id);
    }
    @GetMapping("/mpa")
    public List<Mpa> getAllMpa(){
        return filmService.getAllMpa();
    }
    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable("id") Integer id){
        return filmService.getMpaById(id);
    }
}
