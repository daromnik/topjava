package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

public interface MealRepositoryInterface {

    Meal getById(int id);
    void update(Meal meal);
    void add(Meal meal);
    void delete(int id);

}
