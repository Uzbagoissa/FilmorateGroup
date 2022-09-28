package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String name;

    private LocalDate birthday;

}
