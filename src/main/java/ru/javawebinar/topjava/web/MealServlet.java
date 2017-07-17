package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.DAO.MealDAOImplMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by AlxB on 7/15/2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDAO mealDAO = new MealDAOImplMap();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("GET meals. Action=" + action);

        //String forward="/meals.jsp";
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDAO.getList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_DAY_NORM));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            mealDAO.remove(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("meals");
        } else if (action.equals("edit")) {
            request.setAttribute("meal", mealDAO.getById(Integer.parseInt(request.getParameter("id"))));
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }
        else if (action.equals("add")) {
            request.setAttribute("meal", new Meal(LocalDateTime.now(),"",0));
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        log.debug("Post meals. Id=" + id + ".  " + request.getParameter("button"));
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

