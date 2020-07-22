package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2020, 01, 30, 10, 00), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2020, 01, 30, 13, 00), "Обед", 500);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2020, 01, 30, 20, 00), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(100005, LocalDateTime.of(2020, 01, 30, 00, 00), "Еда на граничное значение", 100);
    public static final Meal MEAL5 = new Meal(100006, LocalDateTime.of(2020, 01, 31, 10, 00), "Завтрак", 1000);
    public static final Meal MEAL6 = new Meal(100007, LocalDateTime.of(2020, 01, 31, 13, 00), "Обед", 500);
    public static final Meal MEAL7 = new Meal(100008, LocalDateTime.of(2020, 01, 31, 20, 00), "Ужин", 410);

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedMealDescription");
        updated.setCalories(300);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }

}
