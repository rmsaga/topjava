package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {
    public void update(Meal meal);

    public void delete(int id);

    public int save(Meal meal);

    public Meal get(int id);

    public List<Meal> getAll();
}
