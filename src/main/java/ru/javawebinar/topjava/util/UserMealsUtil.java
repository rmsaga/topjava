package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<String, Integer> caloriesSummaryPerDay = new HashMap<>();
        // count sum of calories per day
        for (UserMeal meal : meals) {
            String key = String.valueOf(meal.getDateTime().getYear()) +
                    meal.getDateTime().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) +
                    meal.getDateTime().getDayOfMonth();
            if (caloriesSummaryPerDay.containsKey(key)) {
                caloriesSummaryPerDay.put(key, caloriesSummaryPerDay.get(key) + meal.getCalories());
            } else {
                caloriesSummaryPerDay.put(key, meal.getCalories());
            }
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        // generate result list
        for (UserMeal meal : meals) {
            String key = String.valueOf(meal.getDateTime().getYear()) +
                    meal.getDateTime().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) +
                    meal.getDateTime().getDayOfMonth();
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime))
                result.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSummaryPerDay.get(key) > caloriesPerDay));
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
