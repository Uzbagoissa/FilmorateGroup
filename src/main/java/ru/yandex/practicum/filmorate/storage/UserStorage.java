package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.User;

import java.util.Map;

public interface UserStorage {
    User addUser(User user);
    User removeUser(User user);
    User updateUser(User user);
    Map<Integer, User> getUsers();
    User getUserById(Integer userId);
}
