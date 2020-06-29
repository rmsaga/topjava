package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealService;
import ru.javawebinar.topjava.model.MealServiceImplHardList;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    public static List<Meal> hardcodedList = new CopyOnWriteArrayList<>();

    static {
        hardcodedList.addAll(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }

    public static String decodeParameter(String parameter) {
        try {
            return new String(parameter.getBytes("ISO-8859-1"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            log.debug("UnsupportedEncodingException" + "\n" + e.getMessage());
        }
        return parameter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        log.debug("action:" + action);
        // action = edit
        if (action != null && action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            MealService service = new MealServiceImplHardList();
            Meal meal = service.getMealById(id);
            request.setAttribute("meal", meal);
            forward = "meal.jsp";
        }
        // action = delete
        else if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            MealService service = new MealServiceImplHardList();
            if (service.deleteById(id))
                log.debug("Successfully deleted id=" + id);
            else
                log.debug("Can't delete id=" + id);
            response.sendRedirect("meals");
            return;
        }
        // action = add
        else if (action != null && action.equalsIgnoreCase("add")) {
            forward = "meal.jsp";
            log.debug("Forwarding to creating meal page...");
        } else {
            List<MealTo> mealsTO = MealsUtil.filteredByStreams(hardcodedList, LocalTime.MIN, LocalTime.MAX, 2000);
            log.debug("Adding mealsTO to request and forward to meals.jsp");
            request.setAttribute("mealsTO", mealsTO);
            forward = "meals.jsp";
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        //action = add
        if (action != null && action.equalsIgnoreCase("add")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), formatter);
            String description = decodeParameter(request.getParameter("description"));
            int calories = Integer.parseInt(request.getParameter("calories"));
            MealService service = new MealServiceImplHardList();
            int id = service.create(dateTime, description, calories);
            log.debug("Added meal with id=" + id);
        } else {
            // update
            int id = Integer.parseInt(request.getParameter("id"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), formatter);

            String description = decodeParameter(request.getParameter("description"));
            int calories = Integer.parseInt(request.getParameter("calories"));

            log.debug("Updating meal with id= " + id);

            MealService updater = new MealServiceImplHardList();
            if (updater.update(id, dateTime, description, calories))
                log.debug("Successfully updated meal with id=" + id);
            else log.debug("Update failed meal with id=" + id);
        }

        response.sendRedirect("meals");

    }
}


