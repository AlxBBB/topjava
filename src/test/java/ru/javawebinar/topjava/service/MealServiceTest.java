package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

/**
 * Created by AlxB on 30/07/2017.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL2_USER.getId(), USER_ID);
        MATCHER.assertEquals(MEAL2_USER, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getStrange() throws Exception {
        Meal meal = service.get(MEAL2_USER.getId(), ADMIN_ID);
    }


    @Test
    public void getAll() throws Exception {
        List<Meal> mealList = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Stream.of(MEAL1_ADMIN, MEAL2_ADMIN, MEAL3_ADMIN)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()), mealList);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> mealList = service.getBetweenDates(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID);
        MATCHER.assertCollectionEquals(Stream.of(MEAL1_USER, MEAL2_USER, MEAL3_USER, MEAL4_USER, MEAL5_USER, MEAL6_USER)
                .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDateTime.toLocalDate(), endDateTime.toLocalDate()))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()), mealList);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealList = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        MATCHER.assertCollectionEquals(Stream.of(MEAL1_USER, MEAL2_USER, MEAL3_USER, MEAL4_USER, MEAL5_USER, MEAL6_USER)
                .filter(m -> DateTimeUtil.isBetween(m.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()), mealList);
    }


    @Test
    public void save() throws Exception {
        Meal meal = new Meal(LocalDateTime.now(), "Перекус", 300);
        Meal savedMeal = service.save(meal, ADMIN_ID);
        MATCHER.assertCollectionEquals(Stream.of(MEAL1_ADMIN, MEAL2_ADMIN, MEAL3_ADMIN, savedMeal)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()), service.getAll(ADMIN_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ADMIN.getId(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Stream.of(MEAL2_ADMIN, MEAL3_ADMIN)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteStrange() throws Exception {
        service.delete(MEAL1_ADMIN.getId(), USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal meal = service.get(START_MEAL_SEQ, USER_ID);
        meal.setDescription("Test");
        meal.setCalories(10);
        service.update(meal, USER_ID);
        MATCHER.assertEquals(meal, service.get(START_MEAL_SEQ, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateStrange() throws Exception {
        Meal meal = service.get(START_MEAL_SEQ, USER_ID);
        meal.setDescription("Test");
        meal.setCalories(10);
        service.update(meal, ADMIN_ID);
    }
}