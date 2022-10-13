package ru.yandex.practicum.filmorate.exceptions;

public class ValidationConditionException extends RuntimeException {
    public ValidationConditionException(final String message) {
        super(message);
    }
}
