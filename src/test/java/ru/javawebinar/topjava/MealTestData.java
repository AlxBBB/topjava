package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;


public class MealTestData {

    public static final int START_MEAL_SEQ=ADMIN_ID+1;
    public static final int BAD_MEAL_ID=START_MEAL_SEQ-1;

    public static final Meal MEAL1_USER = new Meal( START_MEAL_SEQ,  LocalDateTime.of(2017, Month.JULY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2_USER = new Meal( START_MEAL_SEQ + 1,  LocalDateTime.of(2017, Month.JULY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3_USER = new Meal( START_MEAL_SEQ + 2,  LocalDateTime.of(2017, Month.JULY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4_USER = new Meal( START_MEAL_SEQ + 3,  LocalDateTime.of(2017, Month.JULY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5_USER = new Meal( START_MEAL_SEQ + 4,  LocalDateTime.of(2017, Month.JULY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL6_USER = new Meal( START_MEAL_SEQ + 5,  LocalDateTime.of(2017, Month.JULY, 31, 20, 0), "Ужин", 600);
    public static final Meal MEAL1_ADMIN = new Meal( START_MEAL_SEQ + 6,  LocalDateTime.of(2017, Month.JULY, 30, 10, 10), "Завтрак", 550);
    public static final Meal MEAL2_ADMIN = new Meal( START_MEAL_SEQ + 7,  LocalDateTime.of(2017, Month.JULY, 30, 13, 10), "Обед", 800);
    public static final Meal MEAL3_ADMIN = new Meal( START_MEAL_SEQ + 8,  LocalDateTime.of(2017, Month.JULY, 30, 20, 10), "Ужин", 600);


    public static final LocalDateTime startDateTime=MEAL2_USER.getDateTime();
    public static final LocalDateTime endDateTime=MEAL4_USER.getDateTime();

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>();
}
