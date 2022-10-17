package ru.yandex.practicum.filmorate.exceptions;

public class ValidationFilmByIdException extends RuntimeException {
    public ValidationFilmByIdException(final String message) {
        super(message);
    }
}
