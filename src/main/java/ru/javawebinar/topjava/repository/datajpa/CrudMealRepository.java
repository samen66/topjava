package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
//    @Query(name = Meal.DELETE)
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    List<Meal> findAllByUserAndAllSortedOrderByDateTimeDesc(int userId);

    @Transactional
    @Modifying
//    @Query(name = Meal.DELETE)
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findAllByUser(@Param("userId") int userId);

    List<Meal> findAllByUserAndDateTimeBetween(@NotNull User user, @NotNull LocalDateTime start, @NotNull LocalDateTime end);

    @Transactional
    @Modifying
//    @Query(name = Meal.DELETE)
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@NotNull @Param("userId") int userId,@NotNull @Param("startDateTime") LocalDateTime startDateTime,@NotNull @Param("endDateTime") LocalDateTime endDateTime);
}
