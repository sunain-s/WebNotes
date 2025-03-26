package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private static CategoryManager instance = null;
    private final List<String> categories = new ArrayList<>();
    private final NoteManager noteManager = NoteManager.getInstance();
    private static final String CATEGORY_PATH = "data/categories.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private CategoryManager() {
        loadCategories();
        if (!categories.contains("Uncategorized")) {
            categories.add("Uncategorized"); // Ensure it's only added once
        }
    }

    public static synchronized CategoryManager getInstance() {
        if (instance == null) {
            instance = new CategoryManager();
        }
        return instance;
    }

    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    public void addCategory(String category) {
        if (category != null && !category.trim().isEmpty()) {
            categories.add(category);
            saveCategories();
        }
    }

    public void deleteCategory(String categoryToDelete) {
        if (categoryToDelete == null || categoryToDelete.equals("Uncategorised")) {
            return; // Prevent deletion of "Uncategorized"
        }

        categories.remove(categoryToDelete);
        saveCategories();

        // Update all notes that contained the deleted category
        List<Note> notes = noteManager.getNotes();
        for (Note note : notes) {
            List<String> updatedCategories = note.getCategories().stream()
                    .filter(cat -> !cat.equals(categoryToDelete))
                    .toList();

            // If a note is left with no categories, assign "Uncategorized"
            if (updatedCategories.isEmpty()) {
                updatedCategories = List.of("Uncategorized");
            }

            note.setCategories(updatedCategories);
        }

        // Save changes to notes
        noteManager.publicSaveNotes();
    }

    private void saveCategories() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CATEGORY_PATH), categories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCategories() {
        try {
            File file = new File(CATEGORY_PATH);
            if (file.exists()) {
                List<String> loadedCategories = mapper.readValue(file, new TypeReference<>() {});
                categories.addAll(loadedCategories);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading categories", e);
        }
    }
}
