package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationConditionException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.ValidationFilmByIdException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.interf.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Calendar.DECEMBER;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private Integer id = 1;
    public static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, DECEMBER, 28);

    private static final List<Genre> listGenre = new ArrayList<>();
    private static final List<Mpa> listMpa = new ArrayList<>();

    @Override
    public List<Film> getFilms() {
        log.info("Вернули список фильмов {}", films);
        return (List<Film>) films.values();
    }

    @Override
    public Film addFilm(Film film) {
        CheckOnTheFirstFilm(film);

        if (films.containsKey(film.getId())) {
            throw new ValidationFilmByIdException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже есть в базе");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);

        log.info("В библиотеку добавлен фильм {}", film);
        return film;
    }

    @Override
    public void removeFilm(Film film) {
        validFilmById(film.getId());
        films.remove(film.getId());

        log.info("Из библиотеки удален фильм {}", film);
    }

    @Override
    public Film updateFilm(Film film) {
        CheckOnTheFirstFilm(film);

        validFilmById(film.getId());
        films.put(film.getId(), film);

        log.info("В библиотеке обновлен фильм {}", film);
        return film;
    }

    @Override
    public Film getOrValidFilmById(Integer filmId) {
        validFilmById(filmId);
        log.info("Вернули фильм {}", films);
        return films.get(filmId);
    }

    @Override
    public Film addLikeFromUserById(Integer filmId, Integer userId) {
        Film film = films.get(filmId);
        film.getLikes().add(userId);
        films.put(filmId, film);

        log.info("Пользователь с id: {} поставил like фильму: {}", userId, filmId);
        return film;
    }

    @Override
    public Film removeLikeFromUserById(Integer filmId, Integer userId) {
        Film film = films.get(filmId);
        film.getLikes().remove(userId);

        log.info("Пользователь с id: {} убрал like фильму: {}", userId, filmId);
        return film;
    }

    @Override
    public List<Film> getMostPopularFilmByCountLikes(Integer count) {
        return getFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> getAllGenre() {
        return listGenre;
    }

    @Override
    public Genre getGenreById(Integer id) {
        return listGenre.get(id - 1);
    }

    @Override
    public List<Mpa> getAllMpa() {
        return listMpa;
    }

    @Override
    public Mpa getMpaById(Integer id) {
        return listMpa.get(id - 1);
    }

    private int compare(Film p0, Film p1) {
        return p1.getLikes().size() - p0.getLikes().size();
    }

    private void CheckOnTheFirstFilm(Film film) {
        if (film.getReleaseDate().isBefore(DATE_FIRST_FILM)) {
            throw new ValidationConditionException("Фильм не может быть выпущен ранее: " +
                    DATE_FIRST_FILM);
        }
    }

    private void validFilmById(Integer filmId) {
        if (!films.containsKey(filmId)) {
            throw new ValidationException("Фильм  c id - " + filmId + " не содержится в базе");
        }
    }

    private Integer getNextId() {
        return id++;
    }

    static {
        Genre genre1 = Genre.builder()
                .id(1)
                .name("Комедия")
                .build();
        Genre genre2 = Genre.builder()
                .id(2)
                .name("Драма")
                .build();
        Genre genre3 = Genre.builder()
                .id(3)
                .name("Мультфильм")
                .build();
        Genre genre4 = Genre.builder()
                .id(4)
                .name("Триллер")
                .build();
        Genre genre5 = Genre.builder()
                .id(5)
                .name("Документальный")
                .build();
        Genre genre6 = Genre.builder()
                .id(7)
                .name("Боевик")
                .build();
        listGenre.add(genre1);
        listGenre.add(genre2);
        listGenre.add(genre3);
        listGenre.add(genre4);
        listGenre.add(genre5);
        listGenre.add(genre6);

        Mpa mpa1 = Mpa.builder()
                .id(1)
                .name("G")
                .build();
        Mpa mpa2 = Mpa.builder()
                .id(2)
                .name("PG")
                .build();
        Mpa mpa3 = Mpa.builder()
                .id(3)
                .name("PG-13")
                .build();
        Mpa mpa4 = Mpa.builder()
                .id(4)
                .name("R")
                .build();
        Mpa mpa5 = Mpa.builder()
                .id(5)
                .name("NC-17")
                .build();
        listMpa.add(mpa1);
        listMpa.add(mpa2);
        listMpa.add(mpa3);
        listMpa.add(mpa4);
        listMpa.add(mpa5);
    }
}
