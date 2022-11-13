package ru.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {

    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Size(max=200)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Min(0)
    private int duration;

    private Set<Integer> likes = new HashSet<>();

    private int rate;
    private Mpa mpa;
    private List<Genre> genres;

}
