package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy =  DateReleaseValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateReleaseConstraint {
    String message() default
            "Фильм должен быть выпущен после 1895-12-28";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload() default {};
}
