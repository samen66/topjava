package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
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

        return getAllFilteredMeals(userId, meal -> true);
    }

    @Override
    public List<Meal> betweenHalfOpen(LocalDateTime start, LocalDateTime end, Integer userId) {
        return getAllFilteredMeals(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private List<Meal> getAllFilteredMeals(Integer userId, Predicate<Meal> filter){
        List<Meal> mealList = repository.values().stream().filter(meal -> Objects.equals(meal.getUserId(), userId)).collect(Collectors.toList());
        return CollectionUtils.isEmpty(mealList) ? Collections.emptyList():
                mealList.stream().filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

