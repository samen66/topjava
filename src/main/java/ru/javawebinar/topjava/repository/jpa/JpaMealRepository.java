package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){
            User owner = em.getReference(User.class, userId);
            meal.setUser(owner);
            em.persist(meal);
            return meal;
        }else{
            Meal mealFromDataBase = em.getReference(Meal.class, meal.getId());
            if (mealFromDataBase.getUser().getId() == userId) {
                meal.setUser(mealFromDataBase.getUser());
                em.merge(meal);
                return meal;
            }else{
                throw new NotFoundException("meal not belongs to user");
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal;
        try {
            meal = em.getReference(Meal.class, id);
            if (meal == null)
                return false;
            else if (meal.getUser().getId() != userId)
                return false;

            return em.createNamedQuery(Meal.DELETE)
                    .setParameter("id", id)
                    .executeUpdate() != 0;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal;
        try {
            meal = em.getReference(Meal.class, id);
            if (meal.getUser().getId() != userId)
                return null;
            User eq = em.getReference(User.class, userId);
            meal.setUser(eq);
            return meal;
        }catch (EntityNotFoundException e){
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_USER_MEAL, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> mealList = em.createNamedQuery(Meal.GET_BETWEEN_HALF_AND_OPEN, Meal.class)
                .setParameter("id", userId)
                .setParameter(2, startDateTime)
                .setParameter(3, endDateTime)
                .getResultList();

        return mealList;
    }
}