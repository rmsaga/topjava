package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorageImplHardList implements MealStorage {

    public Map<Integer, Meal> hardcodedMap = new ConcurrentHashMap<>();
    private AtomicInteger maxId = new AtomicInteger(7);
    public static Map<Integer, Meal> testData = new HashMap<>();
    static
    {
        testData.put(Integer.valueOf(1),
                new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        testData.put(Integer.valueOf(2),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        testData.put(Integer.valueOf(3),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        testData.put(Integer.valueOf(4),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        testData.put(Integer.valueOf(5),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        testData.put(Integer.valueOf(6),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        testData.put(Integer.valueOf(7),
                new Meal(7,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public MealStorageImplHardList() {
        hardcodedMap.putAll(testData);
    }

    @Override
    public void update(Meal meal) {
        hardcodedMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        hardcodedMap.remove(id);
    }

    @Override
    public int save(Meal meal) {
        meal.setId(maxId.incrementAndGet());
        hardcodedMap.put(meal.getId(), meal);
        return meal.getId();
    }

    @Override
    public Meal get(int id) {
        return hardcodedMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> result = new ArrayList<>();
        hardcodedMap.forEach((i,m) -> result.add(m));
        return  result;
    }
}
