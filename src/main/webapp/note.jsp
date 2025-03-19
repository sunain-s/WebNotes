<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="uk.ac.ucl.model.Note" %>

<html>
<head>
    <title>Note Details</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <%
        Note note = (Note) request.getAttribute("note");
        if (note != null) {
    %>
    <h1>Note: <%= note.getTitle() %></h1>
        <p><strong>Categories:</strong> <%= String.join(", ", note.getCategories()) %></p>
        <p><strong>Content:</strong> <%= note.getText() %></p>

        <% if (note.getUrl() != null && !note.getUrl().isEmpty()) { %>
            <p><strong>URL:</strong> <a href="<%= note.getUrl() %>"><%= note.getUrl() %></a></p>
        <% } %>

        <% if (note.getImgUrl() != null && !note.getImgUrl().isEmpty()) { %>
            <p><strong>Image:</strong></p>
            <img src="<%= note.getImgUrl() %>" alt="Note Image" width="200">
        <% } %>

        <h2>Edit Note</h2>
        <form action="note" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="oldTitle" value="<%= note.getTitle() %>">
            <label><input type="text" name="newTitle" value="<%= note.getTitle() %>" required></label>
            <label><textarea name="newText"><%= note.getText() %></textarea></label>
            <label><input type="text" name="newUrl" value="<%= note.getUrl() %>"></label>
            <label><input type="text" name="newImgUrl" value="<%= note.getImgUrl() %>"></label>
            <label><input type="text" name="newCategories" value="<%= String.join(", ", note.getCategories()) %>" required></label>
            <button type="submit">Save Changes</button>
        </form>

        <h2>Delete Note</h2>
        <form action="note" method="post">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="title" value="<%= note.getTitle() %>">
            <button type="submit">Delete</button>
        </form>
    <%} else { %>
        <p>Note not found.</p>
    <% }%>

    <br>
    <a href="index">Back to Notes</a>
</body>
</html>
