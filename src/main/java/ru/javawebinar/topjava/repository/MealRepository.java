package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal Meal);

    Meal delete(int id);

    Meal get(int id);

    List<Meal> getAll(LocalDate fromDate, LocalDate toDate, int idUser);
}
