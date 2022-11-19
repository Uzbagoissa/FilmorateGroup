package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.interf.UserStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("daoUserStorage") UserStorage userStorage){
        this.userStorage = userStorage;
    }
    public User getUserById(Integer userId){
        User user = userStorage.getUserById(userId);
        log.info("Возвращен пользователь с id: {}", userId);
        return user;
    }
    public User addUser(User user){
        User curUser = userStorage.addUser(user);
        log.info("В базу добавлен пользователь под id: {}", curUser.getId());
        return curUser;
    }

    public User updateUser(User user){
        User curUser = userStorage.updateUser(user);

        log.info("В базе обновлен пользователь под id: {}", curUser.getId());
        return curUser;
    }


    public List<User> getUsers(){
        List <User> listUser = userStorage.getUsers();
        log.info("Возвращен список пользователей {} содержащихся в базе", listUser.toString());
        return listUser;
    }

    public void removeUser(int id){
        userStorage.removeUser(id);
    }


    public User addFriend(Integer userId, Integer friendId){
        return userStorage.addFriend(userId,friendId);
    }
    public User removeFriend(Integer userId, Integer friendId){
        return userStorage.removeFriend(userId, friendId);
    }
    public Set<User> getFriendsById(Integer userId){
        Set<User> setFriends = userStorage.getFriendsById(userId);

        log.info("Возвращены друзья {} пользователя id: {} ",setFriends.toString(), userId);
        return setFriends;
    }
    public Set<User> getCommonFriends(Integer userId, Integer otherId){
        Set<User> setFriends = userStorage.getCommonFriends(userId, otherId);

        log.info("Возвращены общие друзья {} пользователей c id: {} и {}",setFriends.toString(), userId, otherId);
        return setFriends;
    }
}
