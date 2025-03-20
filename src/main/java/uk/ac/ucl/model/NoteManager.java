package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Collections;

public class NoteManager {
    private static NoteManager nm_instance = null;
    private static List<Note> notes;
    private static final String NOTES_PATH = "data/notes.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private NoteManager() {
        notes = new ArrayList<>();
        loadNotes();
    }

    public static synchronized NoteManager getInstance() {
        if (nm_instance == null) {
            nm_instance = new NoteManager();
        }
        return nm_instance;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Note getNoteByTitle(String title) {
        return notes.stream().filter(n -> n.getTitle().equals(title)).findFirst().orElse(null);
    }

    public List<Note> getNotesByCategory(String category) {
        return notes.stream()
                .filter(n -> n.getCategories().contains(category))
                .collect(Collectors.toList());
    }

    public void addNote(Note note) {
        notes.add(note);
        saveNotes();
    }

    public void editNote(String oldTitle, String newTitle, String newText, String newUrl,  String newImgUrl, List<String> newCategories) {
        Note note = getNoteByTitle(oldTitle);
        if (note != null) {
            note.setTitle(newTitle);
            note.setText(newText);
            note.setUrl(newUrl);
            note.setImgUrl(newImgUrl);
            note.setCategories(newCategories);
            saveNotes();
        }
    }

    private void saveNotes() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(NOTES_PATH), notes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNotes() {
        try {
            File file = new File(NOTES_PATH);
            if (file.exists()) {
                List<Note> loadedNotes = mapper.readValue(file, new TypeReference<>() {
                });
                for (Note note : loadedNotes) {
                    if (note.getCategories() == null || note.getCategories().isEmpty()) {
                        note.setCategories(List.of("Uncategorized")); // Assign a default category
                    }
                }
                notes = loadedNotes;
            } else {
                notes = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNote(String title) {
        notes.removeIf(n -> n.getTitle().equals(title));
        saveNotes();
    }

    public List<Note> searchNotes(String searchTerm) {
        return new NoteSearch().searchNotes(searchTerm, notes);
    }

    public List<Note> getNotesSortedByTitle(List<Note> notesToSort, boolean ascending) {
        List<Note> sortedNotes = new ArrayList<>(notesToSort);
        sortedNotes.sort(Comparator.comparing(Note::getTitle));
        if (!ascending) {
            Collections.reverse(sortedNotes);
        }
        return sortedNotes;
    }

    public List<Note> getNotesSortedByCreatedAt(List<Note> notesToSort, boolean ascending) {
        List<Note> sortedNotes = new ArrayList<>(notesToSort);
        sortedNotes.sort(Comparator.comparing(Note::getCreatedAt));
        if (!ascending) {
            Collections.reverse(sortedNotes);
        }
        return sortedNotes;
    }

    public List<Note> getNotesSortedByEditedAt(List<Note> notesToSort, boolean ascending) {
        List<Note> sortedNotes = new ArrayList<>(notesToSort);
        sortedNotes.sort(Comparator.comparing(Note::getEditedAt));
        if (!ascending) {
            Collections.reverse(sortedNotes);
        }
        return sortedNotes;
    }
}
