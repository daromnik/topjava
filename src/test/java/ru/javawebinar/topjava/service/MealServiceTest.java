package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService mealService;

    @Test
    public void getSuccess() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertThat(meal).isEqualTo(MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getError() {
        Meal meal = mealService.get(MEAL_ID, USER_ID + 1);
        assertThat(meal).isEqualTo(MEAL1);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        assertThat(mealService.getAll(USER_ID)).isEqualTo(Arrays.asList(MEAL2));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(MEAL_ID + 2, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, 5, 30);
        LocalDate endDate = LocalDate.of(2015, 6, 1);
        List<Meal> betweenDates = mealService.getBetweenDates(startDate, endDate, USER_ID);
        assertThat(betweenDates.size()).isEqualTo(2);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertThat(meals.size()).isEqualTo(2);
    }

    @Test
    public void update() {
        Meal mealUpdated = new Meal(MEAL1);
        mealUpdated.setCalories(1100);
        mealUpdated.setDescription("Ланч");
        mealService.update(mealUpdated, USER_ID);
        assertThat(mealService.get(MEAL_ID, USER_ID)).isEqualTo(mealUpdated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(
            LocalDateTime.of(2020, Month.JANUARY, 13, 17, 0),
            "Жрачка",
            5000);
        Meal meal = mealService.create(newMeal, USER_ID);
        newMeal.setId(meal.getId());
        assertThat(mealService.get(meal.getId(), USER_ID)).isEqualTo(newMeal);
    }
}
