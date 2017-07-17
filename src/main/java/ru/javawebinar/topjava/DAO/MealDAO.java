package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by bukreev-a on 17.07.2017.
 */
public interface MealDAO {
    public List<Meal> getList();
    public void add(Meal meal);
    public void update(Meal meal);
    public Meal getById(int id);
    public void remove(int id);
}
