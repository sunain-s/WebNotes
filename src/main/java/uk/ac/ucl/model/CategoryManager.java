package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryManager {
    private static final String CATEGORIES_PATH = "data/categories.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static CategoryManager instance;
    private List<String> categories;

    private CategoryManager() {
        categories = new ArrayList<>();
        loadCategories();
    }

    public static synchronized CategoryManager getInstance() {
        if (instance == null) {
            instance = new CategoryManager();
        }
        return instance;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void addCategory(String category) {
        if (category != null && !category.trim().isEmpty() && !categories.contains(category)) {
            categories.add(category.trim());
            saveCategories();
        }
    }

    private void saveCategories() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CATEGORIES_PATH), categories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCategories() {
        try {
            File file = new File(CATEGORIES_PATH);
            if (file.exists()) {
                    categories = mapper.readValue(file, new TypeReference<>() {
                });
            } else {
                categories = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
