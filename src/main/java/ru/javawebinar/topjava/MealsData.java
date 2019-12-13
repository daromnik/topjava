package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MealsData {

    private static List<Meal> meals = Arrays.asList(
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static List<Meal> getMeals() {
        return meals;
    }

    public static void setMeals(List<Meal> newMeals) {
        meals = newMeals;
    }

    public static List<MealTo> getMealsTo(int maxDayColories) {
        Collection<List<Meal>> mealsGroupByDate = meals.stream().collect(Collectors.groupingBy(Meal::getDate)).values();
        List<MealTo> mealToStream = mealsGroupByDate.stream().flatMap(mealLine -> {
            boolean excess = mealLine.stream().mapToInt(Meal::getCalories).sum() > maxDayColories;
            return mealLine.stream().map(meal ->
                    new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess));
        }).collect(toList());
        return mealToStream;
    }

}
