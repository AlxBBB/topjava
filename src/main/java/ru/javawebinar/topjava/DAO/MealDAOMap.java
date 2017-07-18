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
    private static volatile Integer counter = 0;
    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();


    static {
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.JUNE, 1, 9, 30), "Завтрак", 400));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.JUNE, 1, 12, 15), "Обед", 1100));
        initMap(new Meal(getNextId(), LocalDateTime.of(2015, Month.JUNE, 1, 19, 40), "Ужин", 450));
    }

    private static synchronized int getNextId() {
        return ++counter;
    }

    private static void initMap(Meal meal) {
        meal.setId(getNextId());
        meals.put(meal.getId(), meal);
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
