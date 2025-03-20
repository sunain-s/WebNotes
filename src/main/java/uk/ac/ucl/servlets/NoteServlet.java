package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NoteManager;
import uk.ac.ucl.model.Note;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/note")
public class NoteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        NoteManager manager = NoteManager.getInstance();
        Note note = manager.getNoteByTitle(title);

        if (note == null) {
            response.sendRedirect("/index");
        } else {
            request.setAttribute("note", note);
            request.getRequestDispatcher("/note.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        NoteManager manager = NoteManager.getInstance();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String text = request.getParameter("text");
            String url = request.getParameter("url");
            String imgUrl = request.getParameter("imgUrl");

            // Get multiple selected categories
            String[] selectedCategories = request.getParameterValues("categories");
            List<String> categories = selectedCategories != null ? Arrays.asList(selectedCategories) : List.of();

            if (categories.isEmpty()) {
                response.sendRedirect("/index?error=CategoryRequired");
                return;
            }

            manager.addNote(new Note(title, text, url, imgUrl, categories));
        } else if ("edit".equals(action)) {
            String oldTitle = request.getParameter("oldTitle");
            String newTitle = request.getParameter("newTitle");
            String newText = request.getParameter("newText");
            String newUrl = request.getParameter("newUrl");
            String newImgUrl = request.getParameter("newImgUrl");

            // Get multiple selected categories
            String[] selectedCategories = request.getParameterValues("categories");
            List<String> newCategories = selectedCategories != null ? Arrays.asList(selectedCategories) : List.of();

            manager.editNote(oldTitle, newTitle, newText, newUrl, newImgUrl, newCategories);
        }

        response.sendRedirect("/index");
    }
}
