package ru.yandex.practicum.filmorate.storage.daoImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EmptyResultFromDataBaseException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.interf.FilmStorage;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class DaoFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public DaoFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT * " +
                "FROM films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilms);
    }

    @Override
    public Film getOrValidFilmById(Integer filmId) {
        try {
            String sqlQuery = "SELECT id, name, description, release_date, duration, rate, mpa " +
                    "FROM films " +
                    "WHERE id = ?";

            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilms, filmId);
        } catch (Exception e) {
            throw new ValidationException("Фильм c id: " + filmId + " не содержится в базе");
        }
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO films(name, description, release_date, duration, rate, mpa)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getRate());
            checkMpaIsNull(ps, film);
            return ps;
        }, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(id);

        addOrUpdateGenre(film);

        return getOrValidFilmById(id);
    }

    @Override
    public Film updateFilm(Film film) {
        getOrValidFilmById(film.getId());
        String sqlQuery = "UPDATE films SET " +
                "name = ?, description = ?, release_date = ?, duration = ?, rate = ?, mpa = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getRate()
                , film.getMpa().getId()
                , film.getId());

        addOrUpdateGenre(film);

        return getOrValidFilmById(film.getId());
    }

    @Override
    public void removeFilm(Film film) {

        //удаляем жанры в связанной таблице film_genres
        String sqlQueryGenre = "DELETE " +
                "FROM film_genres " +
                "WHERE id_film = ? ";

        jdbcTemplate.update(sqlQueryGenre, film.getId());

        String sqlQuery = "DELETE " +
                "FROM films " +
                "WHERE id = ?";

        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film addLikeFromUserById(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO likes(id_user, id_film) " +
                "VALUES(?, ?)";

        jdbcTemplate.update(sqlQuery, userId, filmId);
        return getOrValidFilmById(filmId);
    }

    @Override
    public Film removeLikeFromUserById(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE " +
                "FROM likes " +
                "WHERE id_user = ? AND id_film = ? ";

        jdbcTemplate.update(sqlQuery, userId, filmId);
        return getOrValidFilmById(filmId);
    }

    @Override
    public List<Film> getMostPopularFilmByCountLikes(Integer cnt) {
        String sqlQuery = "SELECT f.* " +
                "FROM films AS f " +
                "LEFT JOIN likes AS l ON l.id_film = f.id " +
                "GROUP BY f.id " +
                "ORDER BY COUNT(l.id_user) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilms, cnt);
    }

    @Override
    public List<Genre> getAllGenre() {
        String sqlQuery = "SELECT * " +
                "FROM genres ";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sqlQuery = "SELECT * " +
                "FROM genres " +
                "WHERE id = ?";
        Genre genre;

        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
            return genre;
        } catch (Exception e) {
            throw new EmptyResultFromDataBaseException("Mpa c id: " + id + " не найден");
        }
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery = "SELECT * " +
                "FROM mpa";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    @Override
    public Mpa getMpaById(Integer id) {
        String sqlQuery = "SELECT * " +
                "FROM mpa " +
                "WHERE id = ?";
        try {
            Mpa mpa;
            mpa = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
            return mpa;
        } catch (Exception e) {
            throw new EmptyResultFromDataBaseException("Mpa c id: " + id + " не найден");
        }
    }
    private void checkMpaIsNull(PreparedStatement ps, Film film) throws SQLException {
        if(film.getMpa() != null){
            ps.setInt(6, film.getMpa().getId());
        } else if (film.getMpa() == null)  {
            throw new DataIntegrityViolationException("MPA не может быть null");
        } else if (film.getMpa().getId() < 1 || film.getMpa().getId() > 5) {
            throw new ValidationException("Данного рейтинга еще не существует");
        }
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int i) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
    private Film mapRowToFilms(ResultSet resultSet, int i) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .likes(new HashSet<>(getLikesFromIdsUsers(resultSet.getInt("id"))))
                .rate(resultSet.getInt("rate"))
                .mpa(getMpaById(resultSet.getInt("mpa")))
                .genres(getListGenresByIdFilm(resultSet.getInt("id")))
                .build();
    }

    private Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    private void addOrUpdateGenre(Film film) {
        int id = film.getId();

        if (film.getGenres() != null) {
            String sqlGenre = "DELETE " +
                    "FROM film_genres " +
                    "WHERE id_film = ? ";
            jdbcTemplate.update(sqlGenre, film.getId());

            for (Genre genre : film.getGenres()) {
                sqlGenre = "MERGE INTO film_genres(id_film, id_genre) " +
                        "VALUES (?, ?)";

                jdbcTemplate.update(sqlGenre, id, genre.getId());
            }
        } else {
            String sqlGenre = "DELETE " +
                    "FROM film_genres " +
                    "WHERE id_film = ? ";

            jdbcTemplate.update(sqlGenre, film.getId());
        }
    }

    private List<Genre> getListGenresByIdFilm(int id) {
        String sqlQuery = "SELECT * " +
                "FROM genres " +
                "WHERE id IN" +
                "(" +
                "SELECT id_genre " +
                "FROM film_genres " +
                "WHERE id_film = ?" +
                ")";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, id);
    }

    private List<Integer> getLikesFromIdsUsers(int id) {
        String sqlQuery = "SELECT id " +
                "FROM users " +
                "WHERE id IN" +
                "( " +
                "SELECT id_user " +
                "FROM likes " +
                "WHERE id_film IN" +
                "(" +
                "SELECT id_film " +
                "FROM films " +
                "WHERE id_film = ?" +
                ")" +
                ")";

        return jdbcTemplate.queryForList(sqlQuery, Integer.class, id);
    }
}