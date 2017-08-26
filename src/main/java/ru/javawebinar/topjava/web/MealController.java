package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;
import ru.javawebinar.topjava.web.meal.MealRestController;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class MealController  extends AbstractMealController{

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping(value = "/meals")
    public String all(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String between(HttpServletRequest request, Model model) {
        model.addAttribute("meals", getBetween(parseLocalDate(request.getParameter("startDate")),
                parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")),
                parseLocalTime(request.getParameter("endTime"))));
        return "/meals";
    }

    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable int id, Model model) {
        delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = {"/meals/update/{id}", "/meals/add/{id}"}, method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        final Meal meal = id == -1 ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id") == "") {
            create(meal);
        } else {
            update(meal, Integer.valueOf(request.getParameter("id")));
        }
        return "redirect:/meals";
    }
}
