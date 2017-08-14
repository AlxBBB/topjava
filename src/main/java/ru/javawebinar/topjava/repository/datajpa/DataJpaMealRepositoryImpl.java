package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }

        meal.setUser(crudRepository.getUser(userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal gmeal = crudRepository.findOne(id);
        if (gmeal == null || gmeal.getUser().getId() != userId) {
            return null;
        }
        return gmeal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(startDate,endDate,userId);
    }

    @Override
    @Transactional
    public Meal getWithUser(int id, int userId) {
        Meal gmeal = crudRepository.get(id);
        if (gmeal == null || gmeal.getUser().getId() != userId) {
            return null;
        }
        return gmeal;
    }
}
