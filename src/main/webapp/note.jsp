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
<div class="container">
    <h1>Note: <%= note.getTitle() %></h1>
    <strong>Created At:</strong>
    <%
        String createdAt = note.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(createdAt);
            out.print(sdf.format(date));
        } catch (Exception e) {
            out.print(createdAt);
        }
    %> <br>
    <strong>Edited At:</strong>
    <%
        String editedAt = note.getEditedAt();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(editedAt);
            out.print(sdf.format(date));
        } catch (Exception e) {
            out.print(editedAt);
        }
    %> <br>
    <p><strong>Content:</strong> <%= note.getText() %></p>

    <% if (note.getUrl() != null && !note.getUrl().isEmpty()) { %>
    <p><strong>URL:</strong> <a href="<%= note.getUrl() %>"><%= note.getUrl() %></a></p>
    <% } %>

    <% if (note.getImgUrl() != null && !note.getImgUrl().isEmpty()) { %>
    <p><strong>Image:</strong></p>
    <img src="<%= note.getImgUrl() %>" alt="Note Image" width="200">
    <% } %>

    <h2>Edit Note</h2>
    <form action="note" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="oldTitle" value="<%= note.getTitle() %>">

        <label>Title:</label>
        <input type="text" name="title" value="<%= note.getTitle() %>" required>

        <label>Content:</label>
        <textarea name="text"><%= note.getText() %></textarea>

        <label>URL:</label>
        <input type="text" name="url" value="<%= note.getUrl() %>">

        <h3>Current Image:</h3>
        <% if (note.getImgUrl() != null && !note.getImgUrl().isEmpty()) { %>
        <img src="<%= note.getImgUrl() %>" alt="Note Image" width="200">
        <br>
        <input type="hidden" name="existingImageUrl" value="<%= note.getImgUrl() %>">  <!-- Keep old image -->
        <% } %>

        <label>Upload New Image (optional):</label>
        <input type="file" name="newImage" accept="image/*">

        <h3>Select Categories:</h3>
        <ul class="category-list">
            <%
                List<String> allCategories = CategoryManager.getInstance().getCategories();
                List<String> noteCategories = note.getCategories();
                for (String category : allCategories) {
                    boolean checked = noteCategories.contains(category);
            %>
            <li class="category-item">
                <span><%= category %></span>
                <input type="checkbox" name="categories" value="<%= category %>" <%= checked ? "checked" : "" %>>
            </li>
            <%
                }
            %>
        </ul>

        <button type="submit">Update Note</button>
    </form>

    <form action="note" method="post" style="display:inline;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="title" value="<%= note.getTitle() %>">
        <button type="submit" onclick="return confirm('Are you sure you want to delete this note?')">Delete Note</button>
    </form>

    <br>
    <a href="index" class="back-to-notes">Back to Notes Index</a>
</div>

</body>
</html>
<%
    }
%>
