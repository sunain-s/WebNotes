package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uk.ac.ucl.model.NoteManager;
import uk.ac.ucl.model.Note;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@WebServlet("/note")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB before storing on disk
        maxFileSize = 20 * 1024 * 1024,   // Max file size: 20MB
        maxRequestSize = 25 * 1024 * 1024 // Max request size: 25MB
)
public class NoteServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        NoteManager manager = NoteManager.getInstance();
        String action = request.getParameter("action");

        if ("add".equals(action) || "edit".equals(action)) {
            String title = request.getParameter("title");
            String text = request.getParameter("text");
            String url = request.getParameter("url");

            // Handle image upload
            Part filePart = request.getPart("image");
            String imgUrl = processFileUpload(filePart, request);

            if (request.getAttribute("errorMessage") != null) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return; // Stop further execution if an error occurred
            }

            // Retrieve selected categories
            String[] selectedCategories = request.getParameterValues("categories");
            List<String> categories = (selectedCategories != null && selectedCategories.length > 0)
                    ? Arrays.asList(selectedCategories)
                    : List.of("Uncategorized");

            if ("add".equals(action)) {
                manager.addNote(new Note(title, text, url, imgUrl, categories));
                response.sendRedirect("index"); // 🔥 Redirect after successful add
                return;
            } else {
                String oldTitle = request.getParameter("oldTitle");
                Note existingNote = manager.getNoteByTitle(oldTitle);

                if (existingNote != null) {
                    // Preserve old data if fields are left empty
                    String newTitle = title != null && !title.isEmpty() ? title : existingNote.getTitle();
                    String newText = text != null && !text.isEmpty() ? text : existingNote.getText();
                    String newUrl = url != null && !url.isEmpty() ? url : existingNote.getUrl();

                    // 🔥 Preserve old image if no new one is uploaded
                    String newImgUrl = (imgUrl != null && !imgUrl.isEmpty()) ? imgUrl : existingNote.getImgUrl();

                    manager.editNote(oldTitle, newTitle, newText, newUrl, newImgUrl, categories);
                }

                response.sendRedirect("index"); // 🔥 Redirect after successful edit
                return;
            }
        } else if ("delete".equals(action)) {
            String title = request.getParameter("title");
            if (title != null && !title.isEmpty()) {
                manager.deleteNote(title);
            }
            response.sendRedirect("index"); // 🔥 Redirect after successful delete
            return;
        }
    }




    private String processFileUpload(Part filePart, HttpServletRequest request) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null; // No file uploaded
        }

        // Check file size (server-side validation)
        if (filePart.getSize() > 20 * 1024 * 1024) { // 20MB limit
            request.setAttribute("errorMessage", "The uploaded image is too large! Please select a file smaller than 20MB.");
            return null; // Prevent saving the file
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = request.getServletContext().getRealPath("/") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Create upload directory if it doesn't exist
        }

        // Save file
        File file = new File(uploadPath, fileName);
        filePart.write(file.getAbsolutePath());

        return UPLOAD_DIR + "/" + fileName; // Return relative path for the note
    }
}
