package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.CategoryManager;

import java.io.IOException;
import java.util.List;


@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryManager categoryManager = CategoryManager.getInstance();
        List<String> categories = categoryManager.getCategories();
        request.setAttribute("allCategories", categories);
        request.getRequestDispatcher("/categories.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newCategory = request.getParameter("newCategory");
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            CategoryManager.getInstance().addCategory(newCategory);
        }
        response.sendRedirect("categories");
    }
}

