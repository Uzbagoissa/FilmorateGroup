package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storage.daoImpl.DaoGenreStorage;

import java.util.List;
@Service
@Slf4j
public class GenreService {

    private final DaoGenreStorage daoGenreStorage;

    @Autowired
    public GenreService(DaoGenreStorage daoGenreStorage){
        this.daoGenreStorage = daoGenreStorage;
    }
    public List<Genre> getAllGenre() {
        return daoGenreStorage.getAllGenre();
    }

    public Genre getGenreById(Integer id) {
        return daoGenreStorage.getGenreById(id);
    }
    public List<Genre> getGenresByIdFilm(Integer id) {
        return daoGenreStorage.getGenresByIdFilm(id);
    }
    public void addOrUpdateFilmGenres(Film film) {
        daoGenreStorage.addOrUpdateFilmGenres(film);
    }
}
