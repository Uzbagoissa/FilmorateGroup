package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.services.GenreService;

import java.util.List;
@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {
    @Getter
    GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }
    @GetMapping
    public List<Genre> getAllGenre(){
        return genreService.getAllGenre();
    }
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable("id") Integer id){
        return genreService.getGenreById(id);
    }
}
