package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Genre {
    int id;

    @NotBlank
    @NotNull(message = "Неверные данные: ошибка в записи жанра")
    String name;
}
