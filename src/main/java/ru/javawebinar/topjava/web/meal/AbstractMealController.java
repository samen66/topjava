package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final Integer userId = SecurityUtil.authUserId();

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll meal by id {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }
    public List<Meal> getAllMeal() {
        log.info("getAllMeal meal by id {}", userId);
        return service.getAll(userId);
    }
    public List<MealTo> getBetweenHalfOpen(@Nullable LocalDate startDate, @Nullable LocalDate endDate,
                                           @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        log.info("getBetweenHalfOpen meal by id {}", userId);
        return MealsUtil.getFilteredTos(service.getBetweenHalfOpen(startDate, endDate, userId), SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get {} by meal {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}, meal by userId {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {} meal by userId {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} and by userID={}", meal, id, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}
