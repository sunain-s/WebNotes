package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.NoteManager;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.CategoryManager;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("categories"); // Use "category" instead of "categories"
        String sort = request.getParameter("sort");

        NoteManager manager = NoteManager.getInstance();
        CategoryManager categoryManager = CategoryManager.getInstance();

        // If no category is selected, show category management page
        if (category == null || category.trim().isEmpty()) {
            List<String> categories = categoryManager.getCategories();
            request.setAttribute("allCategories", categories);
            request.getRequestDispatcher("/categories.jsp").forward(request, response);
            return;
        }

        // Filter notes by selected category
        List<Note> allNotes = manager.getNotes();
        List<Note> categoryNotes = allNotes.stream()
                .filter(note -> note.getCategories().contains(category))
                .collect(Collectors.toList());

        // Apply sorting within the selected category
        if ("titleAsc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByTitle(categoryNotes, true);
        } else if ("titleDesc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByTitle(categoryNotes, false);
        } else if ("createdAtAsc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByCreatedAt(categoryNotes, true);
        } else if ("createdAtDesc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByCreatedAt(categoryNotes, false);
        } else if ("editedAtAsc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByEditedAt(categoryNotes, true);
        } else if ("editedAtDesc".equals(sort)) {
            categoryNotes = manager.getNotesSortedByEditedAt(categoryNotes, false);
        }

        // Send filtered and sorted notes to the category page
        request.setAttribute("selectedCategory", category);
        request.setAttribute("notes", categoryNotes);
        request.getRequestDispatcher("/category.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newCategory = request.getParameter("newCategory");
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            CategoryManager.getInstance().addCategory(newCategory);
        }
        response.sendRedirect("categories");
    }
}
