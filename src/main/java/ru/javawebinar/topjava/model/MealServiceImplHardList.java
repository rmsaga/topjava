package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;

public class MealServiceImplHardList implements MealService {
    @Override
    public boolean update(int id, LocalDateTime dateTime, String description, int cal) {
        for (Meal m : MealServlet.hardcodedList) {
            if (m.getId() == id) {
                m.setDateTime(dateTime);
                m.setDescription(description);
                m.setCalories(cal);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        for (int i = 0; i < MealServlet.hardcodedList.size(); i++) {
            if (MealServlet.hardcodedList.get(i).getId() == id) {
                MealServlet.hardcodedList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int create(LocalDateTime dateTime, String description, int cal) {
        Meal m = new Meal(dateTime, description, cal);
        MealServlet.hardcodedList.add(m);
        return m.getId();
    }

    @Override
    public Meal getMealById(int id) {
        for (Meal m : MealServlet.hardcodedList) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
}
