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
    public void addUserWithNullEmail(){
        User user = User.builder()
                .id(0)
                .email(null)
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(user);
                }
        );
        assertEquals("Не введен почтовый адрес", exception.getMessage());
    }

    @Test
    public void addUserWithEmptyEmail(){
        User user = User.builder()
                .id(0)
                .email("  ")
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(user);
                }
        );
        assertEquals("Не введен почтовый адрес", exception.getMessage());
    }

    @Test
    public void addUserWithNullLogin(){
        User user = User.builder()
                .id(0)
                .email("jim@email.com")
                .login(null)
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(user);
                }
        );
        assertEquals("Не введен логин", exception.getMessage());
    }

    @Test
    public void addUserWithEmptyLogin(){
        User user = User.builder()
                .id(0)
                .email("jim@email.com")
                .login("  ")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(user);
                }
        );
        assertEquals("Логин содержит пробелы", exception.getMessage());
    }

    @Test
    public void addUserAsGuestFromFuture(){
        User user = User.builder()
                .id(0)
                .email("jim@email.com")
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(2036, JANUARY, 28))
                .build();

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(user);
                }
        );
        assertEquals("Пользователь из будущего", exception.getMessage());
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
    public void addUserWithIdExistedInBase(){
        User firstUser = User.builder()
                .id(1)
                .email("jim@email.com")
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();

        User secondUser = User.builder()
                .id(1)
                .email("jeff@email.com")
                .login("Jeff")
                .name("Джефф")
                .birthday(LocalDate.of(1955, FEBRUARY, 19))
                .build();

        userController.addUser(firstUser);

        Throwable exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.addUser(secondUser);
                }
        );
        assertEquals("Пользователь - " + secondUser.getName() + " c id - " + secondUser.getId() + " уже есть в базе"
                , exception.getMessage());
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