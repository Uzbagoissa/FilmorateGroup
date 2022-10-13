package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.ValidationUserByIdException;
import ru.yandex.practicum.filmorate.models.User;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private Integer id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        conditionPOSTAndPutUserWithoutValid(user);

        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("В базу добавлен пользователь * {} * зарегестрированный на почтовый ящик: {}"
                , user.getLogin(), user.getEmail());
        return user;
    }

    @Override
    public User removeUser(User user) {
        users.remove(user.getId());

        log.info("Из базы удален пользователь * {} * зарегестрированный на почтовый ящик: {}"
                , user.getLogin(), user.getEmail());
        return null;
    }

    @Override
    public User updateUser(User user) {
        conditionPOSTAndPutUserWithoutValid(user);

        validUser(user);

        users.put(user.getId(), user);
        log.info("В базе обновлен пользователь: {} c id: {}", user.getName(), user.getId());
        return user;
    }
    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(Integer userId) {
        return users.get(userId);
    }

    private void conditionPOSTAndPutUserWithoutValid(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
    private void validUser(User user){
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь - " + user.getName() + " c id - " + user.getId() +
                    " не содержится в базе");
        }
    }
    private Integer getNextId(){
        return id++;
    }
}
