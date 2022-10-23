package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(){
        this.userStorage = new InMemoryUserStorage();
    }
    public List<User> getUsers(){
        log.trace("Количество пользователей в базе: {}", userStorage.getUsers().values());
        return new ArrayList<>(userStorage.getUsers().values());
    }
    public User addUser(User user){
        return userStorage.addUser(user);
    }
    public User updateUser(User user){
        return userStorage.updateUser(user);
    }
    public User removeUser(User user){
        validUserById(user.getId());
        Set<Integer> setFriends = user.getFriends();

        //удалить из списка друзей удаляемого пользователя
        for (Integer key : userStorage.getUsers().keySet()) {
            if(setFriends.contains(key)){
                userStorage.getUsers().get(key).getFriends().remove(user.getId());
            }
        }

        userStorage.removeUser(user);
        return user;
    }
    public User addFriend(Integer userId, Integer friendId){
        validUserById(userId);
        validUserById(friendId);

        User user = userStorage.getUsers().get(userId);
        user.getFriends().add(friendId);

        User friend = userStorage.getUsers().get(friendId);
        friend.getFriends().add(userId);

        log.info("У пользователя с id: {} появился с друг с id: {}", user.getId(), friend.getId());
        return user;
    }
    public User removeFriend(Integer userId, Integer friendId){
        validUserById(userId);
        validUserById(friendId);

        User user = userStorage.getUsers().get(userId);
        user.getFriends().remove(friendId);

        User friend = userStorage.getUsers().get(friendId);
        friend.getFriends().remove(userId);

        log.info("Пользователи с id: {} и {} больше не друзья", user.getId(), friend.getId());
        return user;
    }
    public Set<User> getFriends(Integer userId){
        validUserById(userId);

        User user = userStorage.getUsers().get(userId);

        log.info("У пользователя с id: {} количество друзей: {}", user.getId(), user.getFriends().size());
        return fillUserToSet(user.getFriends());
    }
    public Set<User> getCommonFriends(Integer userId, Integer otherId){
        validUserById(userId);
        validUserById(otherId);

        User user = userStorage.getUsers().get(userId);
        Set<Integer> listUserFriends = user.getFriends();

        User anotherUser = userStorage.getUsers().get(otherId);
        Set<Integer> listAnotherUserFriends = anotherUser.getFriends();

        Set<Integer> intersectionFriends = new HashSet<>(listUserFriends);
        intersectionFriends.retainAll(listAnotherUserFriends);

        log.info("У пользователей с id: {} и {} есть общие друзья с id: {}", user.getId(), anotherUser.getId(),
                intersectionFriends.toArray());

        return fillUserToSet(intersectionFriends);
    }
    public User getUserById(Integer userId){
        validUserById(userId);
        User user = userStorage.getUserById(userId);

        log.info("Возвращен пользователь с id: {}", user.getId());
        return user;
    }
    private void validUserById(Integer userId){
        if (!userStorage.getUsers().containsKey(userId)){
            throw new ValidationException("Пользователь с id: " + userId + " не найден");
        }
    }
    private Set<User> fillUserToSet(Set<Integer> userIdSet){
        Set<User> setUser = new HashSet<>();
        for (Integer userId : userIdSet) {
            setUser.add(userStorage.getUserById(userId));
        }
        return setUser;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
