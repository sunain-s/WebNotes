package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NoteManager;
import uk.ac.ucl.model.Note;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("categories");
        NoteManager manager = NoteManager.getInstance();
        List<Note> categoryNotes = manager.getNotesByCategory(category);
        request.setAttribute("categories", category);
        request.setAttribute("notes", categoryNotes);
        request.getRequestDispatcher("/category.jsp").forward(request, response);
    }
}
