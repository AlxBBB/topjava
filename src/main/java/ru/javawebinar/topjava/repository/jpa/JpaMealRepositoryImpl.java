package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref=em.find(User.class, userId);
            if (ref==null) {
                return null;
            }
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal oldMeal =em.find(Meal.class,meal.getId());
            if (oldMeal==null||userId!=oldMeal.getUser().getId()){
                return null;
            } else {
                meal.setUser(oldMeal.getUser());
                return em.merge(meal);
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal=em.find(Meal.class, id);
        if ((meal==null)||(meal.getUser().getId()!=userId)) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN, Meal.class)
                .setParameter("user_id",userId)
                .setParameter("start_dt",startDate)
                .setParameter("end_dt",endDate)
                .getResultList();
    }
}