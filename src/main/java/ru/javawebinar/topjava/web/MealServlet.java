package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.MealsData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.ListMealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryInterface;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private MealRepositoryInterface mealRepository;

    public MealServlet() {
        mealRepository = new ListMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher;
        String id = req.getParameter("id");
        String delete = req.getParameter("delete");
        if (id != null) {
            Meal meal = mealRepository.getById(Integer.parseInt(id));
            req.setAttribute("meal", meal);
            requestDispatcher = req.getRequestDispatcher("mealEdit.jsp");
        } else {
            if (delete != null) {
                mealRepository.delete(Integer.parseInt(delete));
            }
            List<MealTo> meals = MealsData.getMealsTo(2000);
            req.setAttribute("meals", meals);
            requestDispatcher = req.getRequestDispatcher("meals.jsp");
        }

        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String dd = req.getParameter("dateTime");

        LocalDateTime dateTime = LocalDateTime.parse(
                req.getParameter("dateTime"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
        Meal meal = new Meal(
                Integer.parseInt(req.getParameter("id")),
                dateTime,
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );
        mealRepository.update(meal);
        resp.sendRedirect("meals");
    }
}
