package ru.yandex.practicum.filmorate.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class Event {
    private Long eventId;

    @NotNull
    private Long userId;

    @NotNull
    private Integer entityId;

    @NotNull
    @NotBlank
    private String eventType;

    @NotNull
    @NotBlank
    private String operation;

    private Long timestamp;
}
