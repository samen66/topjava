package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity implements Comparable<Meal>{
    private final Integer userId;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(Integer userId,LocalDateTime dateTime, String description, int calories) {
        this(null,userId, dateTime, description, calories);
    }

    public Meal(Integer id, Integer userId, LocalDateTime dateTime, String description, int calories) {
        super(id);
        synchronized (this){
            this.userId = userId;
            this.dateTime = dateTime;
            this.description = description;
            this.calories = calories;
        }
    }

    public synchronized LocalDateTime getDateTime() {
        return dateTime;
    }
    public LocalDate getLocalDate(){
        return dateTime.toLocalDate();
    }

    public synchronized String getDescription() {
        return description;
    }

    public synchronized int getCalories() {
        return calories;
    }

    public synchronized LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public synchronized LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public synchronized Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    @Override
    public int compareTo(Meal o) {
        return getDateTime().compareTo(o.dateTime);
    }
}
