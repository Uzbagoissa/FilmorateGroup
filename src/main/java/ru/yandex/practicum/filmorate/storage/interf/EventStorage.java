package ru.yandex.practicum.filmorate.storage.interf;

import ru.yandex.practicum.filmorate.models.Event;

import java.util.List;
import java.util.Map;

public interface EventStorage {
    void save(Map<String, Object> data);
    List<Event> getOneById(Long id);
    Map<String, Object> makeEvent(Long userId, Integer entityId, String eventType, String operation);
}
