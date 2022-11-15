package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.daoImpl.DaoMpaStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DaoMpaControllerTest {
    private final DaoMpaStorage daoMpaStorage;
    private static final List<Mpa> listMpa = new ArrayList<>();
    static {
        Mpa mpa1 = Mpa.builder()
                .id(1)
                .name("G")
                .build();
        Mpa mpa2 = Mpa.builder()
                .id(2)
                .name("PG")
                .build();
        Mpa mpa3 = Mpa.builder()
                .id(3)
                .name("PG-13")
                .build();
        Mpa mpa4 = Mpa.builder()
                .id(4)
                .name("R")
                .build();
        Mpa mpa5 = Mpa.builder()
                .id(5)
                .name("NC-17")
                .build();
        listMpa.add(mpa1);
        listMpa.add(mpa2);
        listMpa.add(mpa3);
        listMpa.add(mpa4);
        listMpa.add(mpa5);
    }

    @Test
    public void getAllMpaTest(){
        assertEquals(5, daoMpaStorage.getAllMpa().size());
    }
    @Test
    public void getMpaByIdTest(){
        assertEquals(listMpa.get(2).getName(), daoMpaStorage.getMpaById(3).getName());
    }
}
