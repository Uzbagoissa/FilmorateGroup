package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.daoImpl.DaoMpaStorage;

import java.util.List;
@Service
@Slf4j
public class MpaService {
    private final DaoMpaStorage mpaStorage;


    @Autowired
    public MpaService(DaoMpaStorage mpaStorage){
        this.mpaStorage = mpaStorage;
    }
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Mpa getMpaById(Integer id) {
        return mpaStorage.getMpaById(id);
    }
}
