package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeFilter;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    private Map<Integer, DateTimeFilter> filterDateTime = new ConcurrentHashMap<>();

    @Autowired
    private MealService service;

    public void delete(int id) {
        log.info("delete {} by {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }


    public Meal get(int id) {
        log.info("get {} by {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.save(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} by {}", meal, AuthorizedUser.id());
        checkIdConsistent(meal, id);
        meal.setUserId(AuthorizedUser.id()); // принадлежность установим сейчас, проверка ниже
        service.update(meal);
    }

    public List<Meal> getAllFiltered(LocalDate fromDate, LocalDate toDate) {
        log.info("getAllFiltered");
        return service.getAllFiltered(fromDate, toDate, AuthorizedUser.id());
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }


    public DateTimeFilter getFilter() {
        DateTimeFilter filter = filterDateTime.get(AuthorizedUser.id());
        if (filter == null) {
            filter = new DateTimeFilter();
            filterDateTime.put(AuthorizedUser.id(), filter);
        }
        return filter;
    }

    public List<MealWithExceed> getAllWithExceed() {
        DateTimeFilter filter = getFilter();
        return MealsUtil.getFilteredWithExceeded(filter.noDateFilter() ? getAll() :
                getAllFiltered(filter.takeFromDate(), filter.takeToDate()), filter.takeFromTime(), filter.takeToTime(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void resetFilter() {
        DateTimeFilter filter = getFilter();
        filter.setFromTime(null);
        filter.setToTime(null);
        filter.setFromDate(null);
        filter.setToDate(null);
    }

    public void setFilter(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        DateTimeFilter filter = getFilter();
        filter.setFromDate(fromDate);
        filter.setToDate(toDate);
        filter.setFromTime(fromTime);
        filter.setToTime(toTime);

    }

}