package ru.yandex.practicum.filmorate.storage.interf;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User addUser(User user);
    void removeUser(User user);
    User updateUser(User user);
    List<User> getUsers();
    User getOrValidUserById(Integer userId);

    User addFriend(Integer userId, Integer friendId);

    Set<User> getFriendsById(Integer userId);

    Set<User> getCommonFriends(Integer userId, Integer otherId);

    User removeFriend(Integer userId, Integer friendId);
}
