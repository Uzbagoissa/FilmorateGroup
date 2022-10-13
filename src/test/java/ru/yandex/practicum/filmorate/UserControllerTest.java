package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Month.JANUARY;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.FEBRUARY;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    void setUp() {
        this.userController = new UserController( new UserService());
    }

    @Test
    public void addUserWithNullName() {
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
    public void updateUserWithIdNotExistedInBase() {
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
        assertEquals("Пользователь - " + secondUser.getName() + " c id - " + secondUser.getId() + " не содержится в базе"
                , exception.getMessage());
    }
    @Test
    public void removeUser() {
        User firstUser = User.builder()
                .id(1)
                .email("jim@email.com")
                .login("Jim")
                .name("Джим")
                .birthday(LocalDate.of(1962, JANUARY, 17))
                .build();
        userController.addUser(firstUser);
        userController.removeUser(firstUser);
        assertEquals(new ArrayList<>(0),userController.getUsers());
    }

    @Test
    public void addFriends() {
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
        userController.addUser(secondUser);
        userController.addFriend(1, 2);
                assertEquals(1, userController.getUsers().get(1).getFriends().size());
                assertEquals(1, userController.getUsers().get(1).getFriends().size());
    }

    @Test
    public void getCommonFriends() {
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

        userController.addUser(firstUser);
        userController.addUser(secondUser);
        userController.addUser(thirdUser);
        userController.addFriend(1, 3);
        userController.addFriend(1, 2);
        assertEquals(Stream.of(firstUser).collect(Collectors.toSet()), userController.getCommonFriends(2,3));
    }

}