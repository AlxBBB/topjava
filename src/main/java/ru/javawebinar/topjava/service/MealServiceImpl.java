package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) throws NotFoundException {
        if (!meal.isNew()) {
            get(meal.getId(),meal.getUserId()); //Проверим
        }
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        //2 обращения к репозиторию, лучше не нашел
        get(id, userId); // в get проверим на принадлежность и правильность id
        repository.delete(id); // Не стал перепроверять id.
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal res = checkNotFoundWithId(repository.get(id), id);
        checkNoUserID(res, userId);
        return res;
    }

    @Override
    public void update(Meal meal) throws NotFoundException {
        get(meal.getId(),meal.getUserId()); // Проверим
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll(LocalDate fromDate, LocalDate toDate, int userId) {
        return repository.getAll(fromDate, toDate, userId);
    }

    private static void checkNoUserID(Meal meal, int userId) {
        if (!meal.getUserId().equals(userId)) {
            throw new NotFoundException("Access denied for userId=" + userId);
        }
    }

}