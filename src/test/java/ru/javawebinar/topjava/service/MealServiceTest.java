package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDBMeal.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }
    @Autowired
    private MealService mealService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        Meal actualMeal = mealService.get(MEAL_START_ID, MEAL_START_ID);
        assertMatch(actualMeal, MEAL_USER);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_START_ID, MEAL_START_ID);
        assertThrows(NotFoundException.class,() -> mealService.get(MEAL_START_ID, MEAL_START_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMealList = mealService.getBetweenInclusive(LocalDate.of(2023,3,4), LocalDate.of(2023, 3, 5), MEAL_START_ID);
        assertMatch(filteredMealList, MEAL_USER, MEAL_USER2);
    }

    @Test
    public void getAll() {
        List<Meal> allMealsList = mealService.getAll(MEAL_START_ID);
        assertMatch(allMealsList, MEAL_USER, MEAL_USER2, MEAL_USER3);
    }

    @Test
    public void update() {
        Meal updatedMeal = getUpdatedMeal();
        mealService.update(updatedMeal, MEAL_START_ID);
        assertMatch(mealService.get(MEAL_START_ID, MEAL_START_ID), updatedMeal);
    }

    @Test
    public void create() {
        Meal newMeal = getNewMeal();
        Meal createdMeal = mealService.create(newMeal, MEAL_START_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(newMeal, createdMeal);
    }
}