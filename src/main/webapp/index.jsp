<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<html>
<head>
    <title>Notes Index</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>My Notes</h1>

    <ul>
        <%
            List<Note> notes = (List<Note>) request.getAttribute("notes");
            if (notes != null) {
                for (Note note : notes) {
        %>
        <li>
            <a href="note?title=<%= note.getTitle() %>"><%= note.getTitle() %></a> - Categories:
            <%= String.join(", ", note.getCategories()) %>
        </li>
        <%
                }
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
        <label><input type="text" name="categories" placeholder="Enter categories (comma-separated)" required></label>
        <button type="submit">Add Note</button>
    </form>

    <h2>Search Notes</h2>
    <form action="search" method="post">
        <label><input type="text" name="keyword" placeholder="Search for a note"></label>
        <button type="submit">Search</button>
    </form>

    <h2>Filter by Category</h2>
    <form action="categories" method="get">
        <label><input type="text" name="categories" placeholder="Enter category"></label>
        <button type="submit">Filter</button>
    </form>
</body>
</html>
