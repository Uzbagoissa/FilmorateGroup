package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationConditionException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.time.Month.JANUARY;
import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage.DATE_FIRST_FILM;

class FilmControllerTest {

    FilmController filmController;
    @BeforeEach
    void setUp() {
        this.filmController = new FilmController(new FilmService(new UserService()));
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
                ValidationConditionException.class,
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
    @Test
    public void getMostPopularFilmByCountLikes(){
        User firstUser = User.builder()
                .id(1)
                .email("jim@email.com")
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        User secondUser = User.builder()
                .id(2)
                .email("jeff@email.com")
                .login("Jeff")
                .name("Джефф")
                .birthday(LocalDate.of(1955, FEBRUARY, 19))
                .build();

        User thirdUser = User.builder()
                .id(3)
                .email("Diaz@email.com")
                .login("Cameron")
                .name("Кэмерон")
                .birthday(LocalDate.of(1972, AUGUST, 30))
                .build();

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

        Film thirdFilm = Film.builder()
                .id(3)
                .description("Описание")
                .releaseDate(LocalDate.of(2004, MARCH, 9))
                .duration(108)
                .name("Вечное сияние чистого разума")
                .build();

        filmController.addFilm(firstFilm);
        filmController.addFilm(secondFilm);
        filmController.addFilm(thirdFilm);

        filmController.getFilmService().getUserService().addUser(firstUser);
        filmController.getFilmService().getUserService().addUser(secondUser);
        filmController.getFilmService().getUserService().addUser(thirdUser);

        filmController.getFilmService().addLikeFromUserById(2,1);
        filmController.getFilmService().addLikeFromUserById(2,2);
        filmController.getFilmService().addLikeFromUserById(2,3);

        filmController.getFilmService().addLikeFromUserById(3,1);
        filmController.getFilmService().addLikeFromUserById(3,2);

        filmController.getFilmService().addLikeFromUserById(1,1);

        assertEquals(filmController.getMostPopularFilmByCountLikes(1).size(), 1);
        assertEquals(filmController.getMostPopularFilmByCountLikes(10).size(), 3);
        assertEquals(filmController.getMostPopularFilmByCountLikes(10).size(), 3);

        assertEquals(Collections.singletonList(secondFilm), filmController.getMostPopularFilmByCountLikes(1), "Вернулся неверный списко фильмов");


    }

}