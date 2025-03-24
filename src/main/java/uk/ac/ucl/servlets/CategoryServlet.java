package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.CategoryManager;

import java.io.IOException;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryManager categoryManager = CategoryManager.getInstance();
        request.setAttribute("allCategories", categoryManager.getCategories());
        request.getRequestDispatcher("/categories.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String newCategory = request.getParameter("newCategory");
            if (newCategory != null && !newCategory.trim().isEmpty()) {
                CategoryManager.getInstance().addCategory(newCategory);
            }
        } else if ("delete".equals(action)) {
            String categoryToDelete = request.getParameter("categoryToDelete");
            if (categoryToDelete != null && !categoryToDelete.trim().isEmpty()) {
                CategoryManager.getInstance().deleteCategory(categoryToDelete);
            }
        }
        response.sendRedirect("categories");
    }
}
