package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
//import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    //UserRepository userRepository = new JdbcUserRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        List<User> users = new ArrayList<>();

        Connection connection;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/topjava",
                    "postgres",
                    "Itech_09012019");

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET enabled = ? where email = ?");
            preparedStatement.setBoolean(1,false);
            preparedStatement.setString(2, "useradmin@mail.ru");
            int i = preparedStatement.executeUpdate();

            System.out.println(i);

            Statement statement = connection.createStatement();
            ResultSet selectFromUsers = statement.executeQuery(
                    "SELECT u.*, array_agg(role) as roles FROM users as u LEFT JOIN user_roles ur on u.id = ur.user_id GROUP BY u.id"
            );

            while (selectFromUsers.next()) {
//                System.out.println(selectFromUsers.getInt("id"));
//                System.out.println(selectFromUsers.getString("name"));
//                System.out.println(selectFromUsers.getString("email"));
//                System.out.println(selectFromUsers.getString("password"));
//                System.out.println(selectFromUsers.getInt("calories_per_day"));
//                System.out.println(selectFromUsers.getBoolean("enabled"));
//                System.out.println(selectFromUsers.getDate("registered"));
//
                String roles = selectFromUsers.getString("roles");
                roles = roles.substring(1, roles.length() - 1);
                Collection<Role> rolesCollection = Arrays.stream(roles.split(",")).map(key -> Role.valueOf(key)).collect(Collectors.toList());
//
//                System.out.println(roles);
//                System.out.println(rolesCollection);

                System.out.println("===========================================");
                users.add(new User(
                        selectFromUsers.getInt("id"),
                        selectFromUsers.getString("name"),
                        selectFromUsers.getString("email"),
                        selectFromUsers.getString("password"),
                        selectFromUsers.getInt("calories_per_day"),
                        selectFromUsers.getBoolean("enabled"),
                        selectFromUsers.getDate("registered"),
                        rolesCollection

                ));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

            //List<User> users = userRepository.getAll();
        request.setAttribute("users", users);

        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
