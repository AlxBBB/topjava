package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDAOMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by AlxB on 7/15/2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDAO mealDAO = new MealDAOMap();

    @Override
    public void init() throws ServletException {
        super.init();
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 9, 30), "Завтрак", 400));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 12, 15), "Обед", 1100));
        mealDAO.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 19, 40), "Ужин", 450));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("GET meals. Action=" + action);


        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDAO.getList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_DAY_NORM));
            request.setAttribute("localDateTimeFormat", TimeUtil.dateTimeFormatter);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            mealDAO.remove(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("meals");
        } else if (action.equals("edit")) {
            Meal edMeal = mealDAO.getById(Integer.parseInt(request.getParameter("id")));
            if (edMeal != null) {
                request.setAttribute("meal", edMeal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
            } else {
                response.sendRedirect("meals");
            }
        } else if (action.equals("add")) {
            request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        } else if (action.equals("exit")) {
            response.sendRedirect("index.html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        log.debug("POST meals. Id=" + id + ".  " + request.getParameter("button"));
        if ("Сохранить".equals(request.getParameter("button"))) {
            if (id == 0) {
                mealDAO.add(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))));

            } else {
                mealDAO.update(new Meal(id, LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))));

            }
        }
        response.sendRedirect("meals");
    }

}

