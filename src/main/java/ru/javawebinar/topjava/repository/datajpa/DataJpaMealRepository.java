package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew()) {
            if (meal.getUser().getId() != userId) {
                return null;
            }
        }
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal mealFromDataBase = crudRepository.getReferenceById(id);
        if (mealFromDataBase == null)
            return false;
        if (mealFromDataBase.getUser().getId() != userId) {
            return false;
        }
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal fromDataBase = crudRepository.getReferenceById(id);
        return fromDataBase==null ? null : (fromDataBase.getUser().getId() == userId ? fromDataBase : null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserAndAllSortedOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }
}
