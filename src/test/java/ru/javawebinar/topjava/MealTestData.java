package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    public static final int MEAL_ID = 100002;
    public static final int NOT_FOUND_MEAL = 100009;
    public static final LocalDate START_DATE = LocalDate.of(2020,01,31);
    public static final LocalDate END_DATE = LocalDate.of(2020,01,31);
    public static final List<Meal> MEAL_FILTERED_BY_DATE = Arrays.asList(MEAL5, MEAL6, MEAL7);
    public static final List<Meal> MEALS = Arrays.asList(MEAL4, MEAL1, MEAL2, MEAL3, MEAL5, MEAL6, MEAL7);



    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedMealDescription");
        updated.setCalories(300);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "Food", 2020);
    }

}
