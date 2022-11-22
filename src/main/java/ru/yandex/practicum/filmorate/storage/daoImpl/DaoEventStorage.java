package ru.yandex.practicum.filmorate.storage.daoImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.interf.EventStorage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Primary
@RequiredArgsConstructor
public class DaoEventStorage implements EventStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Map<String, Object> data) {

        if (!data.isEmpty()) {
            StringBuilder sql = new StringBuilder("INSERT INTO events SET ");

            String delimiter = ", ";
            int counter = 1;
            int total = data.size();
            for( String e: data.keySet() ) {
                if (counter == total) {
                    delimiter = ";";
                }
                sql.append(" ").append(e).append(" = ").append("'").append(data.get(e)).append("'").append(delimiter);
                counter++;
            }

            jdbcTemplate.update(sql.toString());
        }
    }

    @Override
    public Map<String, Object> getOneById(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> makeEvent(Long userId, Integer entityId, String eventType, String operation) {
        Map<String, Object> params = new HashMap<>();

        params.put("user_id", userId);
        params.put("entity_id", entityId);
        params.put("entity_type", eventType.toUpperCase(Locale.ROOT));
        params.put("operation", operation.toUpperCase(Locale.ROOT));
        params.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return params;
    }
}
