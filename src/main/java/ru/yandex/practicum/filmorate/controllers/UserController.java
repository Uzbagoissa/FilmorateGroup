package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.Event;
import ru.yandex.practicum.filmorate.models.Film;
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
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer userId){
        return userService.getUserById(userId);
    }
    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }


    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") Integer id){
        userService.removeUser(id);
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
        return userService.getFriendsById(userId);
    }

    @GetMapping(value = "/{id}/feed")
    public List<Event> getFeedByUserId(@PathVariable Integer id) {
        return userService.getFeedByUserId(id);
    }

    @GetMapping("/{id}/recommendations")
    public List<Film> getRecommendations(@PathVariable Integer id) {
        return userService.getRecommendations(id);

    }
}
