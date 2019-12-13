<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>

    <h3><a href="index.html">Home</a></h3>
    <hr>

    <% Meal meal = (Meal) request.getAttribute("meal"); %>

    <h2>Edit Meal #<% out.println(meal.getId()); %></h2>

    <form method="POST" action='meals' name="formAddMeal">
        ID : <input type="text" readonly="readonly" name="id" value="<% out.println(meal.getId()); %>" /><br />
        Дата : <input type="text" name="dateTime" value="<% out.println(meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); %>" /><br />
        Описание : <input type="text" name="description" value="<% out.println(meal.getDescription()); %>" /><br />
        Калорий : <input type="text" name="calories" value="<% out.println(meal.getCalories()); %>" /><br />
        <button type="submit">Сохранить</button>
    </form>

</body>
</html>