<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.CategoryManager" %>

<html>
<head>
    <title>Notes Index</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1>My Notes</h1>

<h2>Sort Notes</h2>
<form action="index" method="get">
    <label>
        <select name="sort">
            <option value="titleAsc" <%= "titleAsc".equals(request.getParameter("sort")) ? "selected" : "" %>>Title (A-Z)</option>
            <option value="titleDesc" <%= "titleDesc".equals(request.getParameter("sort")) ? "selected" : "" %>>Title (Z-A)</option>
            <option value="createdAtAsc" <%= "createdAtAsc".equals(request.getParameter("sort")) ? "selected" : "" %>>Oldest First</option>
            <option value="createdAtDesc" <%= "createdAtDesc".equals(request.getParameter("sort")) ? "selected" : "" %>>Newest First</option>
            <option value="editedAtAsc" <%= "editedAtAsc".equals(request.getParameter("sort")) ? "selected" : "" %>>Last Edited (Oldest First)</option>
            <option value="editedAtDesc" <%= "editedAtDesc".equals(request.getParameter("sort")) ? "selected" : "" %>>Last Edited (Newest First)</option>
        </select>
    </label>
    <button type="submit">Sort</button>
</form>

<ul>
    <%
        List<Note> notes = (List<Note>) request.getAttribute("notes");
        if (notes != null && !notes.isEmpty()) {
            for (Note note : notes) {
    %>
    <li>
        <a href="note?title=<%= note.getTitle() %>"><%= note.getTitle() %></a> - Categories:
        <%= String.join(", ", note.getCategories()) %>
    </li>
    <%
        }
    } else {
    %>
    <p>No notes found.</p>
    <%
        }
    %>
</ul>

<h2>Add a New Note</h2>
<form action="note" method="post">
    <input type="hidden" name="action" value="add">
    <label><input type="text" name="title" placeholder="Title" required></label>
    <label><textarea name="text" placeholder="Note Content"></textarea></label>
    <label><input type="text" name="url" placeholder="URL"></label>
    <label><input type="text" name="imgUrl" placeholder="Image URL"></label>

    <h3>Select Categories:</h3>
    <%
        List<String> allCategories = CategoryManager.getInstance().getCategories();
        if (allCategories.isEmpty()) {
    %>
    <p>No categories available. <a href="categories">Create categories first</a>.</p>
    <%
    } else {
        for (String category : allCategories) {
    %>
    <label><input type="checkbox" name="categories" value="<%= category %>"></label>
    <%= category %> <br>
    <%
            }
        }
    %>

    <button type="submit">Add Note</button>
</form>

<h2>Search Notes</h2>
<form action="search" method="post">
    <label><input type="text" name="keyword" placeholder="Search for a note"></label>
    <button type="submit">Search</button>
</form>

<h2>Filter by Category</h2>
<form action="index" method="get">
    <label>Select Category:</label>
    <label><select name="category">
        <option value="">-- Select a Category --</option>
        <%
            String selectedCategory = (String) request.getAttribute("selectedCategory"); // Keep selection
            for (String category : allCategories) {
        %>
        <option value="<%= category %>" <%= category.equals(selectedCategory) ? "selected" : "" %>><%= category %></option>
        <%
            }
        %>
    </select></label>
    <button type="submit">Filter</button>
</form>


</body>
</html>
