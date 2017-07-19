package ru.javawebinar.topjava.DAO;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by bukreev-a on 17.07.2017.
 */

public class MealDAOMap implements MealDAO {
    private static final Logger log = getLogger(MealDAOMap.class);
    private static Integer counter = 0;
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();


    private static synchronized int getNextId() {
        return ++counter;
    }


    @Override
    public List<Meal> getList() {
        log.debug("get list from " + meals.size() + " meals");
        return new ArrayList<Meal>(meals.values());
    }

    @Override
    public void add(Meal meal) {
        log.debug("Add " + meal);
        meal.setId(getNextId());
        meals.put(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        if (meal.getId() == 0) {
            log.debug("UPDATE->ADD meal");
            add(meal);
        }
        log.debug("Update meal with id=" + meal.getId());
        meals.replace(meal.getId(), meal); //Если был удален, пока редактировали, то удалили
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public void remove(int id) {
        log.debug("Delete meal with id=" + id);
        meals.remove(id);
    }
}
