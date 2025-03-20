<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<html>
<head>
    <title>Category: <%= request.getAttribute("categories") %></title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Notes in Category: <%= request.getAttribute("categories") %></h1>

    <h2>Sort Notes in Category: <%= request.getAttribute("categories") %></h2>
    <form action="categories" method="get">
        <input type="hidden" name="categories" value="<%= request.getAttribute("categories") %>">
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
                <p>No notes found in this category.</p>
        <%  }%>
    </ul>

    <br>
    <a href="index">Back to Notes</a>
</body>
</html>
