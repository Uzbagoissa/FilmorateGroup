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
    public void addFilmWithNullName(){
        Film film = Film.builder()
                .id(0)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(101)
                .name(null)
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Название фильма не может быть пустым", exception.getMessage());
    }

    @Test
    public void addFilmWithEmptyName(){
        Film film = Film.builder()
                .id(0)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(101)
                .name(" ")
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Название фильма не может быть пустым", exception.getMessage());
    }

    @Test
    public void addFilmWithLengthMoreThan200(){
        Film film = Film.builder()
                .id(0)
                .name("Маска")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(101)
                .description("Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще " +
                        "рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает " +
                        "способность превращаться в неуязвимое мультяшное существо с озорным характером." +
                        "Режиссер Чак Рассел заявил, что изначально проект «Маска» затевался как достаточно жесткий фильм" +
                        " ужасов на основе одноименного комикса, однако впоследствии трансформировался в комедию, ставшую " +
                        "бенефисом Джима Керри")
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Описание фильма не может быть больше 200 символов", exception.getMessage());
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
    public void addFilmWithZeroDuration(){
        Film film = Film.builder()
                .id(0)
                .description("Описание")
                .releaseDate(LocalDate.of(1994, JULY, 28))
                .duration(0)
                .name("Маска")
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.addFilm(film);
                }
        );
        assertEquals("Фильм не может идти меньше 0 минут", exception.getMessage());
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