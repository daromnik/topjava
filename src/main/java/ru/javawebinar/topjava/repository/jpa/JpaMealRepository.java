package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }

        meal.setUser(entityManager.find(User.class, userId));

        if (meal.isNew()) {
            entityManager.persist(meal);
        } else {
            entityManager.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createQuery("DELETE FROM Meal m WHERE m.id = :id AND m.user.id = :user_id")
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createQuery("SELECT m FROM Meal m WHERE m.user.id = :user_id ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
        // SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC
    }

    @Override
    public List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int userId) {
        return entityManager.createQuery("SELECT m FROM Meal m WHERE m.user.id = :user_id AND (m.dateTime BETWEEN :startDate AND :endDate) ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("user_id", userId)
                .setParameter("startDate", getStartInclusive(startDate))
                .setParameter("endDate", getEndExclusive(endDate))
                .getResultList();
    }
}