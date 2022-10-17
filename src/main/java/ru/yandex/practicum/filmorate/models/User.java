package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {

    private int id;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    private String name;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();
}
