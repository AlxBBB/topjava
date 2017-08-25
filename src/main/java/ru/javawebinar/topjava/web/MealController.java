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
    public String between(@RequestParam("startDate") String startDate,
                          @RequestParam("endDate") String endDate,
                          @RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime,
                          Model model) {
        model.addAttribute("meals", getBetween(parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate), parseLocalTime(endTime)));
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
    public String save(@RequestParam("id") Integer id, @RequestParam("dateTime") String dateTime,
                       @RequestParam("description") String description, @RequestParam("calories") Integer calories, Model model) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (id == null) {
            create(meal);
        } else {
            update(meal, id);
        }
        return "redirect:/meals";
    }
}
