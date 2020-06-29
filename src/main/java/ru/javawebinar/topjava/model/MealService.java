package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public interface MealService {
    public boolean update(int id, LocalDateTime dateTime, String description, int cal);

    public boolean deleteById(int id);

    public int create(LocalDateTime dateTime, String description, int cal);

    public Meal getMealById(int id);
}
