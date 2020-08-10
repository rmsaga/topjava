package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        return em.merge(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId");
        return query
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        return meal == null ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC");
        return query
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = em.createQuery(
                "SELECT m FROM Meal m " +
                        "WHERE m.user.id=:userId " +
                        "AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime " +
                        "ORDER BY m.dateTime DESC"
        );
        return query
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}