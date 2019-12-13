<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<%
    List<MealTo> meals = (List<MealTo>) request.getAttribute("meals");

    if (meals != null && !meals.isEmpty()) {
        out.println("<ul>");

        for (MealTo meal : meals) {
            String colorClass = meal.getExcess() ? "red" : "green";
            out.println("<li style='color: "
                    + colorClass + "'>"
                    + meal.getDate()
                    + ": " + meal.getDescription()
                    + " <a href='?id=" + meal.getId() + "'>Изменить</a>"
                    + " <a href='?delete=" + meal.getId() + "'>Удалить</a></li>"
            );
        }
        out.println("</ul>");
    } else {
        out.println("<p>There are no users yet!</p>");
    }
%>

</body>
</html>