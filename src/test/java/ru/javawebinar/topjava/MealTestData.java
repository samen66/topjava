package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_START_ID = AbstractBaseEntity.START_SEQ;
    public static final int NOT_FOUND = 40;
    //2023-03-05 13:15:00.000000
    //2023-03-04 14:15:00.000000
    public static final Meal MEAL_USER = new Meal(MEAL_START_ID, LocalDateTime.of(2023, 3,5,13,15, 0, 0), "lunch", 700);
    public static final Meal MEAL_USER2 = new Meal(MEAL_START_ID+1, LocalDateTime.of(2023, 3,5,16,15, 0, 0), "uzhen", 500);
    public static final Meal MEAL_USER3 = new Meal(MEAL_START_ID+2, LocalDateTime.of(2023, 3,6,13,15, 0, 0), "lunch", 700);

    public static final Meal MEAL_ADMIN = new Meal(MEAL_START_ID+3, LocalDateTime.of(2023, 3,4,14,15, 0, 0), "after lunch", 700);

    public static Meal getNewMeal(){
        return new Meal(null, LocalDateTime.of(2023, 3,6,8,0, 0, 0), "breakfast", 700);
    }

    public static Meal getUpdatedMeal(){
        Meal updatedMeal = new Meal(MEAL_USER);
        updatedMeal.setCalories(500);
        updatedMeal.setDescription("uzhn");
        updatedMeal.setDateTime(LocalDateTime.of(2023, 3, 5, 20, 0, 0, 0));
        return updatedMeal;
    }
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("user_id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("user_id").isEqualTo(expected);
    }
}
