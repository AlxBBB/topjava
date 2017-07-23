package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void delete(int id, int userId)
    {
        log.info("delete {} by {}", id, userId);
        service.delete(id, userId);
    }


    public Meal get(int id, int userId ){
        log.info("get {} by {}", id, userId);
        return service.get(id,userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.save(meal, userId);
    }

    public void update(Meal meal, int userId)
    {
        log.info("update {} by {}", meal, userId);
        service.update(meal,userId);
    }

    public List<Meal> getAll(int userId)
    {
        log.info("getAll");
        return service.getAll(userId);
    }





}