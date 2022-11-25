package ru.yandex.practicum.filmorate.storage.daoImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EmptyResultFromDataBaseException;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
@Slf4j
public class DaoMpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public DaoMpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mpa> getAllMpa() {
        String sqlQuery = "SELECT * " +
                "FROM mpa";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    public Mpa getMpaById(Integer id) {
        String sqlQuery = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?";
        try {
            Mpa mpa;
            mpa = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
            return mpa;
        } catch (Exception e) {
            log.info("Mpa c id: {} не найден", id);
            throw new EmptyResultFromDataBaseException("Mpa c id: " + id + " не найден");
        }
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int i) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
