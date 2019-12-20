package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<MealTo> getByDateAndTime(
            LocalDate startDate, LocalTime startTime,
            LocalDate endDate, LocalTime endTime)
    {
        log.info("getByDateAndTime startDate: {}, startTime: {}, endDate: {}, endTime: {}, ",
                startDate, startTime, endDate, endTime);
        return null;
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return null;
    }

    public Meal deleteById(int id) {
        log.info("deleteById {}", id);
        return null;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return null;
    }

    public Meal update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        return null;
    }

}