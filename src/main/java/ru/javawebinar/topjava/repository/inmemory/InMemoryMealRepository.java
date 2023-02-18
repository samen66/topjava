package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, null));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.getUserId() != userId)
            return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (repository.get(id).getUserId() != userId)
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (repository.get(id).getUserId() != userId)
            return null;
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {

        return repository.values().stream().filter(meal -> Objects.equals(meal.getUserId(), userId))
                .sorted(Meal::compareTo).collect(Collectors.toList());
    }
}

