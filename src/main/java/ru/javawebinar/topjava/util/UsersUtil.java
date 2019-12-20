package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Roman", "daromnik@yandex.ru", "123456", Role.ROLE_ADMIN),
            new User(null, "Nela", "nela@gmail.com", "123456", Role.ROLE_USER),
            new User(null, "Amir", "amir@mail.ru", "123456", Role.ROLE_USER)
    );
}