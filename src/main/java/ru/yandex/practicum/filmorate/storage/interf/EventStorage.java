package ru.yandex.practicum.filmorate.storage.interf;

import java.util.Map;

public interface EventStorage {
    void save(Map<String, Object> data);
    Map<String, Object> getOneById(Long id);
    Map<String, Object> makeEvent(Long userId, Integer entityId, String eventType, String operation);
}
