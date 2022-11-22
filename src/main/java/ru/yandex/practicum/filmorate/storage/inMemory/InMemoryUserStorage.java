package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.exceptions.ValidationUserByIdException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.interf.UserStorage;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private Integer id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        conditionPOSTAndPutUserWithoutValid(user);

        if (users.containsKey(user.getId())) {
            throw new ValidationUserByIdException("Пользователь - " + user.getName() + " c id - " + user.getId()
                    + " уже есть в базе");
        }

        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("В базу добавлен пользователь * {} * зарегестрированный на почтовый ящик: {}"
                , user.getLogin(), user.getEmail());
        return user;
    }

    @Override
    public void removeUser(User user) {
        validUser(user);
        if(user.getFriends() != null){
            List<Integer> setFriends = new ArrayList<>(user.getFriends());
            //удалить из списка друзей удаляемого пользователя
            for (Integer key : getMapUsers().keySet()) {
                if(setFriends.contains(key)){
                    getMapUsers().get(key).getFriends().remove(user.getId());
                }
            }
        }


        users.remove(user.getId());
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
    public List<User> getUsers() {
        return new ArrayList<>(users.values()) ;
    }
    @Override
    public User getUserById(Integer userId) {
        validUser(users.get(userId));
        return users.get(userId);
    }
    @Override
    public User addFriend(Integer userId, Integer friendId){
        validUser(getUserById(userId));
        validUser(getUserById(friendId));

        User user = getMapUsers().get(userId);
        user.setFriends(new HashSet<>());
        user.getFriends().add(friendId);

        User friend = getMapUsers().get(friendId);
        friend.setFriends(new HashSet<>());
        friend.getFriends().add(userId);

        log.info("У пользователя с id: {} появился друг с id: {}", user.getId(), friend.getId());
        return user;
    }
    @Override
    public Set<User> getFriendsById(Integer userId){
        validUser(getUserById(userId));

        User user = getUsers().get(userId);

        log.info("У пользователя с id: {} количество друзей: {}", user.getId(), user.getFriends().size());
        return fillUserToSet(user.getFriends());
    }
    @Override
    public Set<User> getCommonFriends(Integer userId, Integer otherId){
        validUser(getUserById(userId));
        validUser(getUserById(otherId));

        User user = getMapUsers().get(userId);
        Set<Integer> listUserFriends = user.getFriends();

        User anotherUser = getMapUsers().get(otherId);
        Set<Integer> listAnotherUserFriends = anotherUser.getFriends();

        Set<Integer> intersectionFriends = new HashSet<>(listUserFriends);
        intersectionFriends.retainAll(listAnotherUserFriends);

        log.info("У пользователей с id: {} и {} есть общие друзья с id: {}", user.getId(), anotherUser.getId(),
                intersectionFriends.toArray());

        return fillUserToSet(intersectionFriends);
    }
    @Override
    public User removeFriend(Integer userId, Integer friendId){
        validUser(getUserById(userId));
        validUser(getUserById(friendId));

        User user = getUsers().get(userId);
        user.getFriends().remove(friendId);

        User friend = getUsers().get(friendId);
        friend.getFriends().remove(userId);

        log.info("Пользователи с id: {} и {} больше не друзья", user.getId(), friend.getId());
        return user;
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
    private Set<User> fillUserToSet(Set<Integer> userIdSet){
        Set<User> setUser = new HashSet<>();
        for (Integer userId : userIdSet) {
            setUser.add(getUserById(userId));
        }
        return setUser;
    }
    private Map<Integer, User> getMapUsers() {
        return users;
    }
    private Integer getNextId(){
        return id++;
    }
}
