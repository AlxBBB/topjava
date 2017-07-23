package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal delete(int id) {
        return repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }



    @Override
    public List<Meal> getAll(int idUser) {
        return repository.values().stream().filter(v -> v.getUserId().equals(idUser))
                .sorted((v, w) -> w.getDateTime().compareTo(v.getDateTime()))
                .collect(Collectors.toList());
    }
}

