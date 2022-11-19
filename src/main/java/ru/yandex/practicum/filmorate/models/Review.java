package ru.yandex.practicum.filmorate.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class Review {
    private Long id;

    @Positive(message = "Введите корректный идентификатор пользователя")
    private Integer userId;

    @Positive(message = "Введите корректный идентификатор фильма")
    private Integer filmId;

    @NotNull(message = "Поле Отзыв обязательно для заполнения")
    @NotBlank(message = "Текст отзыва не может быть пустым")
    private String content;

    @NotNull
    private Boolean isPositive;

    @NotNull
    private Long useful;
}
