package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.debug("Getting All meal for userId={}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.debug("Getting filtered list for userId={}", SecurityUtil.authUserId());
        return MealsUtil.getFilteredTos(service.getAllByDate(getFilteredMinDate(startDate),
                getFilteredMaxDate(endDate), SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(),
                getFilteredMinTime(startTime), getFilteredMaxTime(endTime));
    }

    public Meal get(int id) {
        log.debug("Getting mealId={} for userId={}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.debug("Creating mealId={} for userId={}", meal.getId(), SecurityUtil.authUserId());
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public Meal update(Meal meal) {
        log.debug("Updating mealId={} for userId={}", meal.getId(), SecurityUtil.authUserId());
        assureIdConsistent(meal, SecurityUtil.authUserId());
        return service.update(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.debug("Deleting mealId={} for userId={}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
    }


}