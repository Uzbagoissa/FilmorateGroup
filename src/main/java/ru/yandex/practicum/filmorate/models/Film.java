package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Size(max=200)
    private String description;

    private LocalDate releaseDate;

    @Min(0)
    private int duration;

}
