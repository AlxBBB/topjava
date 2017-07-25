package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void delete(int id)
    {
        log.info("delete {} by {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }


    public Meal get(int id){
        log.info("get {} by {}", id, AuthorizedUser.id());
        return service.get(id,AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.save(meal);
    }

    public void update(Meal meal)
    {
        log.info("update {} by {}", meal, AuthorizedUser.id());
        meal.setUserId(AuthorizedUser.id());
        service.update(meal);
    }

    public List<Meal> getAll(LocalDate fromDate, LocalDate toDate)
    {
        log.info("getAll");
        return service.getAll(fromDate,toDate,AuthorizedUser.id());
    }





}