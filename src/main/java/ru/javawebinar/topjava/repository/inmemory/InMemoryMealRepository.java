package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);


    // Map(userId, Map(id, Meal))
    private Map<Integer, Map<Integer, Meal>> usersMealsRepository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, MealsUtil.USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.computeIfAbsent(userId, u -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        } else if (!userMeals.containsKey(meal.getId())) {
            log.info("Cannot update meal with id={}, meal doesn't exist or doesn't belong to user with userid={}", meal.getId(), userId);
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (!userMeals.containsKey(id)) {
            log.info("Cannot delete meal with id={}, meal doesn't exist or doesn't belong to user with userid={}", id, userId);
            return false;
        }
        return userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = usersMealsRepository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (!userMeals.containsKey(id)) {
            log.info("Cannot get meal with id={}, meal doesn't exist or doesn't belong to user with userid={}", id, userId);
            return null;
        }
        return userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return MealsUtil.filterByPredicate(usersMealsRepository.getOrDefault(userId, null), meal -> true);
    }

    @Override
    public Collection<Meal> getAllByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return MealsUtil.filterByPredicate(usersMealsRepository.getOrDefault(userId, null),
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));

    }
}

