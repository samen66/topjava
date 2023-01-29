package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 23, 59), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<String, Integer> sumCaloriesPerDsy = new HashMap<>();

        for(UserMeal userMeal : meals){
            String timeUser = dateForm(userMeal.getDateTime());
            if(sumCaloriesPerDsy.containsKey(timeUser)){
                sumCaloriesPerDsy.put(timeUser, sumCaloriesPerDsy.get(timeUser) + userMeal.getCalories());
            }else
                sumCaloriesPerDsy.put(timeUser, userMeal.getCalories());
        }

        for (UserMeal userMeal : meals){
            boolean isBetweenTime = TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime);

            if (isBetweenTime){
                boolean isGreaterCalories = caloriesPerDay < sumCaloriesPerDsy.get(dateForm(userMeal.getDateTime()));
                userMealWithExcessList.add(
                        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isGreaterCalories)
                );
            }
        }

        return userMealWithExcessList;
    }

    private static String dateForm(LocalDateTime time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(
                Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> userDate = meals.stream().collect(
                Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories))
        );
        System.out.println(userDate);
         return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> newUserExcess(userMeal, (userMeal.getCalories() > userDate.get(userMeal.getLocalDate()))))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        userMealWithExcessList.add(newUserExcess(meals.get(0), true));
        return userMealWithExcessList;
    }

    public static UserMealWithExcess newUserExcess(UserMeal userMeal, boolean isExess){
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isExess);
    }
}
