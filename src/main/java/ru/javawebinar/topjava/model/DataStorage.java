package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AlxB on 7/15/2017.
 */
public class DataStorage {
    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 9, 30), "Завтрак", 400),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 12, 15), "Обед", 1100),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 19, 40), "Ужин", 450)
    );

    public static List<Meal> getMeals() {
        return meals;
    }
}
