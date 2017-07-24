package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    private Map<Integer, DateTimeFilter> filterDateTime = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");


        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), AuthorizedUser.id(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal, AuthorizedUser.id());
        } else {
            mealRestController.update(meal, AuthorizedUser.id());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        DateTimeFilter filter = filterDateTime.get(AuthorizedUser.id());
        ;
        if (filter == null) {
            filter = new DateTimeFilter();
            filterDateTime.put(AuthorizedUser.id(), filter);
        }

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id, AuthorizedUser.id());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request), AuthorizedUser.id());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "nofilter":
                filter.setFromTime(null);
                filter.setToTime(null);
                filter.setFromDate(null);
                filter.setToDate(null);
                response.sendRedirect("meals");
                break;
            case "filter":
                filter.setFromTime(request.getParameter("fromTime").equals("") ? null : LocalTime.parse(request.getParameter("fromTime")));
                filter.setToTime(request.getParameter("toTime").equals("") ? null : LocalTime.parse(request.getParameter("toTime")));
                filter.setFromDate(request.getParameter("fromDate").equals("") ? null : LocalDate.parse(request.getParameter("fromDate")));
                filter.setToDate(request.getParameter("toDate").equals("") ? null : LocalDate.parse(request.getParameter("toDate")));
                response.sendRedirect("meals");
                break;

            case "all":
            default:
                log.info("getAll");

                request.setAttribute("userId", AuthorizedUser.id());
                request.setAttribute("filter", filter);
                request.setAttribute("meals",
                        MealsUtil.getFilteredWithExceeded(mealRestController.getAll(filter.takeFromDate(), filter.takeToDate(),
                                AuthorizedUser.id()), filter.takeFromTime(),filter.takeToTime(),MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
