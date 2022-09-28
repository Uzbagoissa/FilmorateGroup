package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j

@RequestMapping("/users")
public class UserController {
    int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers(){
        log.trace("Количество пользователей в базе: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        conditionPOSTAndPutUser(user);

        if(users.containsKey(user.getId())){
            throw new ValidationException("Пользователь - " + user.getName() + " c id - " + user.getId() +
                    " уже есть в базе");
        }
        user.setId(id++);
        log.info("В базу добавлен пользователь * {} * зарегестрированный на почтовый ящик: {}"
                , user.getLogin(),user.getEmail());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        conditionPOSTAndPutUser(user);

        if (!users.containsKey(user.getId())){
            throw new ValidationException("Пользователь - " + user.getName() + " c id - " + user.getId() +
                    " не содержится в базе");
        }
        users.put(user.getId(), user);
        log.info("В базе обновлен пользователь: {} c id: {}", user.getName(), user.getId());
        return user;
    }

    private void conditionPOSTAndPutUser(User user){
        if (user.getEmail() == null || user.getEmail().isBlank()){
            throw new ValidationException("Не введен почтовый адрес");
        }
        if (user.getLogin() == null || user.getEmail().isBlank()){
            throw new ValidationException("Не введен логин");
        }
        if (user.getLogin().contains(" ")){
            throw new ValidationException("Логин содержит пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Пользователь из будущего");
        }
    }

}
