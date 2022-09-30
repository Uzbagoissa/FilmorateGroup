package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.Month.DECEMBER;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    public static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, DECEMBER, 28);

    @GetMapping
    public List<Film> getFilms(){
        log.trace("Количество фильмов в базе: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film){
        conditionPOSTAndPutFilmWithoutValid(film);

        if(films.containsKey(film.getId())){
            throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже есть в базе");
        }

        film.setId(id++);
        films.put(film.getId(), film);
        log.info("В библиотеку добавлен фильм {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film){
        conditionPOSTAndPutFilmWithoutValid(film);

        if(!films.containsKey(film.getId())){
            throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() +
                    " не содержится в базе");
        }

        films.put(film.getId(), film);
        log.info("В библиотеке обновлен фильм {}", film);
        return film;
    }

    private void conditionPOSTAndPutFilmWithoutValid(Film film){
        if (film.getReleaseDate().isBefore(DATE_FIRST_FILM)){
            throw new ValidationException("Фильм не может быть выпущен ранее: " +
                    DATE_FIRST_FILM);
        }
    }

}
