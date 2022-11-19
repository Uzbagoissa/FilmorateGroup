package ru.yandex.practicum.filmorate.storage.daoImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.interf.ReviewStorage;

@Component
@Primary
@RequiredArgsConstructor
public class DaoReviewStorage implements ReviewStorage {

    private final JdbcTemplate jdbcTemplate;
}
