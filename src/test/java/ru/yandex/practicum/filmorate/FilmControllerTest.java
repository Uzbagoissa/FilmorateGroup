package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;

import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JULY;
import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.controllers.FilmController.DATE_FIRST_FILM;

class FilmControllerTest {

    FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void addFilmWithWrongDate(){
        Film film = Film.builder()
                .id(0)
                .description("Описание")
                .releaseDate(LocalDate.of(1825, DECEMBER, 14))
                .duration(101)
                .name("Маска")
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Фильм не может быть выпущен ранее: " +
                DATE_FIRST_FILM, exception.getMessage());
    }

    @Test
    public void addFilmWithIdExistedFilmInBase(){
        Film firstFilm = Film.builder()
                .id(1)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(101)
                .name("Маска")
                .build();

        Film secondFilm = Film.builder()
                .id(1)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, DECEMBER, 6))
                .duration(107)
                .name("Тупой и еще тупее")
                .build();
        filmController.addFilm(firstFilm);

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(secondFilm);
                }
        );
        assertEquals("Фильм - " + secondFilm.getName() + " c id - " + secondFilm.getId() + " уже есть в базе",
                exception.getMessage());
    }

    @Test
    public void updateFilmWithIdNotExistedFilmInBase(){
        Film firstFilm = Film.builder()
                .id(1)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(101)
                .name("Маска")
                .build();

        Film secondFilm = Film.builder()
                .id(3)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, DECEMBER, 6))
                .duration(107)
                .name("Тупой и еще тупее")
                .build();
        filmController.addFilm(firstFilm);

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.updateFilm(secondFilm);
                }
        );
        assertEquals("Фильм - " + secondFilm.getName() + " c id - " + secondFilm.getId() +
                        " не содержится в базе",
                exception.getMessage());
    }

}