package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import uk.ac.ucl.model.NoteManager;
import uk.ac.ucl.model.Note;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoteManager manager = NoteManager.getInstance();
        List<Note> notes = manager.getNotes();
        request.setAttribute("notes", notes);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
