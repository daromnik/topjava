package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.MealsData;
import ru.javawebinar.topjava.model.Meal;

import java.util.stream.Collectors;

public class ListMealRepository implements MealRepositoryInterface {

    @Override
    public Meal getById(int id) {
        return MealsData.getMeals().stream().filter(m -> m.getId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public void update(Meal meal) {
        MealsData.getMeals().stream().filter(m -> m.getId() == meal.getId()).map(m -> {
            m.setDateTime(meal.getDateTime());
            m.setCalories(meal.getCalories());
            m.setDescription(meal.getDescription());
            return m;
        }).count();
    }

    @Override
    public void add(Meal meal) {

    }

    @Override
    public void delete(int id) {
        MealsData.setMeals(MealsData.getMeals().stream().filter(m -> m.getId() != id).collect(Collectors.toList()));
    }
}
