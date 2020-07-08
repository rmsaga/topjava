package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);


    // Map(userId, Map(id, Meal))
    private Map<Integer, Map<Integer, Meal>> usersMealsRepository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        } else if (meal.getUserId() != userId) {
            log.info("Denied updating someome else's meal MealID={}", meal.getId());
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        if (userMeals.get(id).getUserId() != userId) {
            log.info("Denyed deleting someone else's meal. MealID={}", id);
            return false;
        }
        return userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        if (userMeals.get(id).getUserId() != userId) {
            log.info("Getting someone else's meal was canceled. MealID={}", id);
            return null;
        }
        return userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        return userMeals.values().stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> get(LocalDate startDate, LocalDate endDate, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        LocalDate min = (startDate == null) ? LocalDate.MIN : startDate.minusDays(1);
        LocalDate max = (endDate == null) ? LocalDate.MAX : endDate.plusDays(1);
        return userMeals.values().stream()
                .filter(meal -> meal.getDate().isAfter(min) && meal.getDate().isBefore(max))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }
}

