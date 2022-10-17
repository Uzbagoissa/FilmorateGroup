package ru.yandex.practicum.filmorate.exceptions;

public class ValidationUserByIdException extends RuntimeException {
    public ValidationUserByIdException(final String message) {
        super(message);
    }
}
