package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.Director;
import ru.yandex.practicum.filmorate.services.DirectorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/directors")
public class DirectorController {
    @Getter
    DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService){
        this.directorService = directorService;
    }
    @GetMapping
    public List<Director> getAllDirector(){
        log.info("Запрос списка режиссеров из базы");
        return directorService.getAllDirector();
    }

    @GetMapping("/{id}")
    public Director getDirectorById(@PathVariable("id") Integer id){
        log.info("Запрос режиссера с id: {} из базы", id);
        return directorService.getDirectorById(id);
    }

    @PostMapping
    public Director addDirector(@Valid @RequestBody Director director) {
        log.info("Запрос добавления режиссера с имененем: {} в базе", director.getName());
        return directorService.addDirector(director);
    }

    @PutMapping
    public Director updateDirector(@Valid @RequestBody Director director) {
        log.info("Запрос обновления режиссера с id: {} в базе", director.getId());
        return directorService.updateDirector(director);
    }

    @DeleteMapping("/{id}")
    public void removeDirector(@PathVariable("id") Integer id) {
        log.info("Запрос удаления режиссера с id: {} в базе", id);
        directorService.removeDirector(id);
    }
}
