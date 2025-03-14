<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Search Results</h1>

    <ul>
        <%
            List<Note> results = (List<Note>) request.getAttribute("results");
            if (results != null && !results.isEmpty()) {
                for (Note note : results) {
        %>
        <li>
            <a href="note?title=<%= note.getTitle() %>"><%= note.getTitle() %></a> - <%= note.getCategory() %>
        </li>
        <%
            }
        } else {
        %>
        <p>No notes found matching your search.</p>
        <%
            }
        %>
    </ul>

    <br>
    <a href="index">Back to Notes</a>
</body>
</html>
