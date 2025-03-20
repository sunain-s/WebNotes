<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Manage Categories</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>Manage Categories</h1>

<h2>Existing Categories</h2>
<ul>
  <%
    List<String> categories = (List<String>) request.getAttribute("allCategories");
    for (String category : categories) {
  %>
  <%= category %>
  <%
    }
  %>
</ul>

<h2>Add a New Category</h2>
<form action="categories" method="post">
  <input type="text" name="newCategory" placeholder="Category Name" required>
  <button type="submit">Add Category</button>
</form>

<br>
<a href="index">Back to Notes</a>
</body>
</html>
