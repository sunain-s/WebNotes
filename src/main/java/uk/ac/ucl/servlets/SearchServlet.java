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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        NoteManager manager = NoteManager.getInstance();
        List<Note> results = manager.searchNotes(keyword);
        request.setAttribute("results", results);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}