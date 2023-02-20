package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Controller
public class MealRestController extends AbstractMealController{

    @Override
    public List<MealTo> getAll() {

        return super.getAll();
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, id);
    }

    @Override
    public List<MealTo> getBetweenHalfOpen(@Nullable LocalDate startDate,@Nullable LocalDate endDate,
                                           @Nullable LocalTime startTime,@Nullable LocalTime endTime) {
        return super.getBetweenHalfOpen(startDate, endDate, startTime, endTime);
    }
}