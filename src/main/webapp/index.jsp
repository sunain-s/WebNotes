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
            <option value="createdAtAsc" <%= "createdAtAsc".equals(request.getParameter("sort")) ? "selected" : "" %>>Oldest First</option>
            <option value="createdAtDesc" <%= "createdAtDesc".equals(request.getParameter("sort")) ? "selected" : "" %>>Newest First</option>
            <option value="titleAsc" <%= "titleAsc".equals(request.getParameter("sort")) ? "selected" : "" %>>Title (A-Z)</option>
            <option value="titleDesc" <%= "titleDesc".equals(request.getParameter("sort")) ? "selected" : "" %>>Title (Z-A)</option>
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
<form action="note" method="post" enctype="multipart/form-data">  <!-- Add enctype -->
    <input type="hidden" name="action" value="add">
    <label><input type="text" name="title" placeholder="Title" required></label>
    <label><textarea name="text" placeholder="Note Content"></textarea></label>
    <label><input type="text" name="url" placeholder="URL"></label>

    <label>Upload Image:</label>
    <input type="file" name="image" accept="image/*">

    <h3>Select Categories:</h3>
    <%
        List<String> allCategories = CategoryManager.getInstance().getCategories();
        for (String category : allCategories) {
    %>
    <label><input type="checkbox" name="categories" value="<%= category %>"></label>
    <%= category %> <br>
    <%
        }
    %>

    <button type="submit">Add Note</button>
</form>



<h2>Manage Categories</h2>
<a href="categories">
    <button>Go to Category Management</button>
</a>


</body>
</html>
