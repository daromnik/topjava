package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(
            MEAL_ID,
            LocalDateTime.of(2015, Month.JUNE, 1, 14, 0),
            "Админ ланч",
            510
    );

    public static final Meal MEAL2 = new Meal(
            MEAL_ID + 1,
            LocalDateTime.of(2015, Month.JUNE, 1, 21, 00),
            "Админ ужин",
            1500
    );

    public static final Meal MEAL3 = new Meal(
            MEAL_ID + 2,
            LocalDateTime.of(2015, Month.MAY, 30, 10, 30),
            "Завтрак",
            500
    );


//    public static void assertMatch(User actual, User expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
//    }

//    public static void assertMatch(Iterable<User> actual, User... expected) {
//        assertMatch(actual, Arrays.asList(expected));
//    }
//
//    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
//        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
//    }
}
