<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Manage Categories</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
  <h1>Manage Categories</h1>

  <h2>Existing Categories</h2>
  <ul>
    <%
      List<String> categories = (List<String>) request.getAttribute("allCategories");
      if (categories.isEmpty()) {
    %>
    <p>No categories available.</p>
    <%
    } else {
      for (String category : categories) {
    %>
    <li class="category-item">
      <span class="category-name"><%= category %></span>
      <% if (!category.equals("Uncategorized")) { %>
      <form action="categories" method="post" class="delete-form">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="categoryToDelete" value="<%= category %>">
        <button type="submit" class="delete-category" onclick="return confirm('Are you sure you want to delete this category?')">Delete</button>
      </form>
      <% } %>
    </li>
    <%
        }
      }
    %>
  </ul>

  <h2>Add a New Category</h2>
  <form action="categories" method="post">
    <input type="hidden" name="action" value="add">
    <label><input type="text" name="newCategory" placeholder="Category Name" required></label>
    <button type="submit">Add Category</button>
  </form>

  <br>
  <a href="index" class="back-to-notes">Back to Notes Index</a>
</div>

</body>
</html>
