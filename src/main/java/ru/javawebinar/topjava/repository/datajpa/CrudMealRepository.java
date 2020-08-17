package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(int id, int user_id);

    @Query("SELECT Meal FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    Meal get(int id, int userId);

    @Query("SELECT Meal FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND m.dateTime <= :endDateTime")
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
