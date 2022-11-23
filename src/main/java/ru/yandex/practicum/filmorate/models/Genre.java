package ru.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class Genre {
    int id;

    @NotBlank
    @NotNull(message = "Неверные данные: ошибка в записи жанра")
    String name;
}
