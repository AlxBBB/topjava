package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    Meal save(Meal meal) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal) throws NotFoundException;

    List<Meal> getAll(int userId);

    List<Meal> getAllFiltered(LocalDate fromDate, LocalDate toDate,int userId);


}