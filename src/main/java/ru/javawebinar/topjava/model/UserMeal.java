package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class UserMeal {
    private static HashMap<LocalDate, Integer> caloriesSummaryPerDay = new HashMap<>();
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        caloriesSummaryPerDay.put(dateTime.toLocalDate(), caloriesSummaryPerDay.getOrDefault(dateTime.toLocalDate(), 0) + calories);
    }

    public static int getCaloriesSummaryPerDay(LocalDate day) {
        return caloriesSummaryPerDay.get(day);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
