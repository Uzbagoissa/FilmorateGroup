package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static java.time.Month.JANUARY;
import static java.util.Calendar.FEBRUARY;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;
    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    public void addUserWithNullName(){
        User user = User.builder()
                .id(0)
                .email("jim@email.com")
                .login("Jim")
                .name(" ")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();
        userController.addUser(user);
        assertEquals("Jim", user.getName());
    }

    @Test
    public void updateUserWithIdNotExistedInBase(){
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

        userController.addUser(firstUser);

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.updateUser(secondUser);
                }
        );
        assertEquals("Пользователь - " + secondUser.getName() + " c id - " + secondUser.getId() +  " не содержится в базе"
                , exception.getMessage());
    }
}