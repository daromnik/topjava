package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement preparedStatement = connection.prepareStatement(
                                    "INSERT INTO meals (user_id, dateTime, description, calories) VALUES (?, ?, ?, ?)",
                                    Statement.RETURN_GENERATED_KEYS
                            );
                            preparedStatement.setInt(1, userId);
                            preparedStatement.setTimestamp(2, Timestamp.valueOf(meal.getDateTime()));
                            preparedStatement.setString(3, meal.getDescription());
                            preparedStatement.setInt(4, meal.getCalories());
                            return preparedStatement;
                        }
                    }, keyHolder);
            //keyHolder.getKey().intValue()
            meal.setId((int)keyHolder.getKeys().get("id"));
        } else {
            jdbcTemplate.update("UPDATE meals SET user_id = ?,  description = ?, calories = ? WHERE id = ?",
                    userId,
                    meal.getDescription(),
                    meal.getCalories(),
                    meal.getId()
            );
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return (jdbcTemplate.update("DELETE FROM meals WHERE id = ? AND user_id = ?", id, userId) != 0);
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id = ? AND user_id = ?", ROW_MAPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ?", ROW_MAPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? AND (dateTime BETWEEN ? AND ?)", ROW_MAPER, userId, startDate, endDate);
    }
}
