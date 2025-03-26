<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date, java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="uk.ac.ucl.model.CategoryManager" %>

<html>
<%
    Note note = (Note) request.getAttribute("note");
    if (note != null) {
%>
<head>
    <title><%= note.getTitle() %></title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

    <h1>Note: <%= note.getTitle() %></h1>
    <strong>Created At:</strong>
    <%
        String createdAt = note.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(createdAt);
            out.print(sdf.format(date));
        } catch (Exception e) {
            out.print(createdAt); // In case parsing fails, fallback to raw string
        }
    %> <br>
    <strong>Edited At:</strong>
    <%
        String editedAt = note.getEditedAt();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(editedAt);
            out.print(sdf.format(date));
        } catch (Exception e) {
            out.print(editedAt); // In case parsing fails, fallback to raw string
        }
    %> <br>
        <p><strong>Content:</strong> <%= note.getText() %></p>

        <% if (note.getUrl() != null && !note.getUrl().isEmpty()) { %>
            <p><strong>URL:</strong> <a href="<%= note.getUrl() %>"><%= note.getUrl() %></a></p>
        <% } %>

    <%
        if (note.getImgUrl() != null && !note.getImgUrl().isEmpty()) {
    %>
            <p><strong>Image:</strong></p>
            <img src="<%= note.getImgUrl() %>" alt="Note Image" width="200">
    <%
        }
    %>

    <h2>Edit Note</h2>
    <form action="note" method="post" enctype="multipart/form-data">  <!-- Enable file uploads -->
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="oldTitle" value="<%= note.getTitle() %>">

        <label>Title:</label>
        <label>
            <input type="text" name="newTitle" value="<%= note.getTitle() %>" required>
        </label>

        <label>Content:</label>
        <label>
            <textarea name="newText"><%= note.getText() %></textarea>
        </label>

        <label>URL:</label>
        <label>
            <input type="text" name="newUrl" value="<%= note.getUrl() %>">
        </label>

        <h3>Current Image:</h3>
        <% if (note.getImgUrl() != null && !note.getImgUrl().isEmpty()) { %>
        <img src="<%= note.getImgUrl() %>" alt="Note Image" width="200">
        <br>
        <input type="hidden" name="existingImageUrl" value="<%= note.getImgUrl() %>">  <!-- Keep old image -->
        <% } %>

        <label>Upload New Image (optional):</label>
        <input type="file" name="newImage" accept="image/*">

        <h3>Select Categories:</h3>
        <%
            List<String> allCategories = CategoryManager.getInstance().getCategories();
            for (String category : allCategories) {
                boolean isChecked = note.getCategories().contains(category);
        %>
        <label>
            <input type="checkbox" name="categories" value="<%= category %>" <%= isChecked ? "checked" : "" %>>
            <%= category %>
        </label><br>
        <%
            }
        %>

        <button type="submit">Save Changes</button>
    </form>

    <h2>Delete Note</h2>
        <form action="note" method="post" onsubmit="return confirm('Are you sure you want to delete this note')">
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
