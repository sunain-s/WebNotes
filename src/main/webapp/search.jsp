<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Search Results</h1>

    <ul>
        <%
            List<Note> results = (List<Note>) request.getAttribute("results");
            if (results != null && !results.isEmpty()) {
                for (Note note : results) {
        %>
        <li>
            <a href="note?title=<%= note.getTitle() %>"><%= note.getTitle() %></a> - Categories:
            <%= String.join(", ", note.getCategories()) %>
        </li>
        <%
            }
        } else {
        %>
        <p>No notes found matching your search.</p>
        <%  }%>
    </ul>

    <br>
    <a href="index" class="back-to-notes">Back to Notes Index</a>
</div>
</body>
</html>
