package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@Service
public class MealService {

    private MealRepository repository;

    public List<MealTo> getAll() {
        return repository.getAll();
    }
}