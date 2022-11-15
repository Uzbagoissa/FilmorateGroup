package ru.yandex.practicum.filmorate.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;

    @Email(message = "Неверные данные: ошибка в записи email")
    private String email;

    @NotNull
    @NotBlank(message = "Неверные данные: имя пустое или содержит только пробелы")
    private String login;

    private String name;

    @Past(message = "Неверные данные: День рождения не может быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();
}
