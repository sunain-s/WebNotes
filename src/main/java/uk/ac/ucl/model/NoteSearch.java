package uk.ac.ucl.model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class NoteSearch {
    public List<Note> searchNotes(String keyword, List<Note> notes) {
        List<Note> result = new ArrayList<>();
        List<String> searchWords = Arrays.asList(keyword.toLowerCase().trim().split("\\s+"));
        for (Note note : notes) {
            String title = note.getTitle().toLowerCase();
            String text = note.getText().toLowerCase();
            String url = note.getUrl().toLowerCase();
            String imgUrl = note.getImgUrl().toLowerCase();
            List<String> categories = note.getCategories().stream().map(String::toLowerCase).toList();
            // Search in all data fields
            boolean match = searchWords.stream().anyMatch(word ->
                            title.contains(word) ||
                            text.contains(word) ||
                            url.contains(word) ||
                            imgUrl.contains(word) ||
                            categories.stream().anyMatch(category -> category.contains(word))
            );

            if (match) {
                result.add(note);
            }
        }
        return result;
    }
}
