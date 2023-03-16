package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meal")
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m where m.id=:id"),
        @NamedQuery(name = Meal.GET_ALL_USER_MEAL, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_HALF_AND_OPEN, query = "SELECT m FROM Meal m WHERE m.user.id=:id AND m.dateTime >= ?2 AND m.dateTime < ?3 ORDER BY m.dateTime DESC")
})
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL_USER_MEAL = "Meal.getAllUsersMeal";
    public static final String GET_BETWEEN_HALF_AND_OPEN = "Meal.getBetweenHalfAndOpen";
    @Column(name = "DATE_TIME", nullable = false, unique = true)
    private LocalDateTime dateTime;
    @Column(name = "DESCRIPTION")
    @Size(max = 255)
    private String description;
    @Column(name = "CALORIES")
    private int calories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getLocalDate() {
        return dateTime.toLocalDate();
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
