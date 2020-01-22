package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id =:id AND m.user.id =:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(int userId);

    Meal getByIdAndUserId(int id, int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id =:user_id AND m.dateTime >= :start_date AND m.dateTime < :end_date ORDER BY m.dateTime DESC")
    List<Meal> getAllByUserIdAndGetBetweenDates(
            @Param("start_date") LocalDateTime startDate,
            @Param("end_date") LocalDateTime endDate,
            @Param("user_id") int userId
    );
}
