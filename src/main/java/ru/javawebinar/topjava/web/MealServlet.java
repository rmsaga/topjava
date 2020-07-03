package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealStorage;
import ru.javawebinar.topjava.dao.MealStorageHardMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_LIMIT = 2000;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private MealStorage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new MealStorageHardMap();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "default";
        log.debug("doGet action:" + action);
        switch (action) {
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = storage.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                storage.delete(id);
                response.sendRedirect("meals");
                break;
            case "add":
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                log.debug("Forwarding to create meal page...");
                break;
            default:
                List<MealTo> mealsTO = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_LIMIT);
                log.debug("Adding mealsTO to request and forward to meals.jsp");
                request.setAttribute("mealsTO", mealsTO);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "default";
        log.debug("doPost action:" + action);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), FORMATTER);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = request.getParameter("id").equals("Will be generated") ? 0 : Integer.parseInt(request.getParameter("id"));
        Meal meal = new Meal(id, dateTime, description, calories);
        switch (action) {
            case "add":
                id = storage.save(meal);
                log.debug("Added meal with id=" + id);
                break;
            default:
                storage.update(meal);
                log.debug("Updated meal with id=" + id);
        }
        response.sendRedirect("meals");

    }
}


