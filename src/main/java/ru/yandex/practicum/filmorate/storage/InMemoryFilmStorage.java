package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationConditionException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.ValidationFilmByIdException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.util.Calendar.DECEMBER;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 1;
    public static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, DECEMBER, 28);

    @Override
    public Film addFilm(Film film) {
        conditionPOSTAndPutFilmWithoutValid(film);

        if (films.containsKey(film.getId())) {
            throw new ValidationFilmByIdException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже есть в базе");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);

        log.info("В библиотеку добавлен фильм {}", film);
        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        validFilm(film);
        films.remove(film.getId());

        log.info("Из библиотеки удален фильм {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        conditionPOSTAndPutFilmWithoutValid(film);

        validFilm(film);
        films.put(film.getId(), film);

        log.info("В библиотеке обновлен фильм {}", film);
        return film;
    }

    @Override
    public Map<Integer, Film> getFilms() {

        log.info("Вернули список фильмов {}", films);
        return films;
    }
    @Override
    public Film getFilm(Integer filmId) {
        validFilmById(filmId);
        log.info("Вернули фильм {}", films);
        return films.get(filmId);
    }
    private void conditionPOSTAndPutFilmWithoutValid(Film film) {
        if (film.getReleaseDate().isBefore(DATE_FIRST_FILM)) {
            throw new ValidationConditionException("Фильм не может быть выпущен ранее: " +
                    DATE_FIRST_FILM);
        }
    }
    private void validFilm(Film film){
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() +
                    " не содержится в базе");
        }
    }
    private void validFilmById(Integer filmId){
        if (!films.containsKey(filmId)){
            throw new ValidationException("Фильм  c id - " + filmId + " не содержится в базе");
        }
    }
    private Integer getNextId(){
        return id++;
    }
}
