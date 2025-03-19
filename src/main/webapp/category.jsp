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
