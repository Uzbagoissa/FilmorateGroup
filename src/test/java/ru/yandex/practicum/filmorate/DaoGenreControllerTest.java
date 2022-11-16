package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.storage.daoImpl.DaoGenreStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DaoGenreControllerTest {
    private static final List<Genre> listGenre = new ArrayList<>();
    private final DaoGenreStorage daoGenreStorage;
    static {
        Genre genre1 = Genre.builder()
                .id(1)
                .name("Комедия")
                .build();
        Genre genre2 = Genre.builder()
                .id(2)
                .name("Драма")
                .build();
        Genre genre3 = Genre.builder()
                .id(3)
                .name("Мультфильм")
                .build();
        Genre genre4 = Genre.builder()
                .id(4)
                .name("Триллер")
                .build();
        Genre genre5 = Genre.builder()
                .id(5)
                .name("Документальный")
                .build();
        Genre genre6 = Genre.builder()
                .id(6)
                .name("Боевик")
                .build();
        listGenre.add(genre1);
        listGenre.add(genre2);
        listGenre.add(genre3);
        listGenre.add(genre4);
        listGenre.add(genre5);
        listGenre.add(genre6);
    }
    @Test
    public void getAllMpaTest(){
        assertEquals(6, daoGenreStorage.getAllGenre().size());
    }
    @Test
    public void getMpaByIdTest(){
        assertEquals(listGenre.get(2).getName(), daoGenreStorage.getGenreById(3).getName());
    }
}
