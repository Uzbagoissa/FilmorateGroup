package ru.yandex.practicum.filmorate.exceptions;

public class EmptyResultFromDataBaseException extends RuntimeException {
    public EmptyResultFromDataBaseException(final String message) {
        super(message);
    }
}