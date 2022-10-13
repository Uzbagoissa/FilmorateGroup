package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j

@RequestMapping("/users")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer userId){
        return userService.getUserById(userId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping
    public User removeUser(@RequestBody User user){
        return userService.removeUser(user);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer userId, @PathVariable("friendId") Integer friendId){
        return userService.addFriend(userId, friendId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") Integer userId, @PathVariable("friendId") Integer friendId){
        return userService.removeFriend(userId, friendId);
    }
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable("id") Integer userId, @PathVariable("otherId") Integer otherId){
        return userService.getCommonFriends(userId, otherId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable("id") Integer userId){
        return userService.getFriends(userId);
    }
}
