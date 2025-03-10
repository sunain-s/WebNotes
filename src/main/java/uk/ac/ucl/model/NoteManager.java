package uk.ac.ucl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addNote(Note note) {
        notes.add(note);
        saveNotes();
    }

    public void editNote(String oldTitle, String newTitle, String newText, String newUrl,  String newImgUrl, String newCategory) {
        Note note = getNoteByTitle(oldTitle);
        if (note != null) {
            note.setTitle(newTitle);
            note.setText(newText);
            note.setUrl(newUrl);
            note.setImgUrl(newImgUrl);
            note.setCategory(newCategory);
            saveNotes();
        }
    }

    private void saveNotes() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(NOTES_PATH), notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNotes() {
        try {
            File file = new File(NOTES_PATH);
            if (file.exists()) {
                notes = mapper.readValue(file, new TypeReference<List<Note>>() {
                });
            } else {
                notes = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNote(String title) {
        notes.removeIf(n -> n.getTitle().equals(title));
    }

    public List<Note> searchNotes(String searchTerm) {
        return notes.stream().filter(n -> n.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                n.getText().toLowerCase().contains(searchTerm.toLowerCase())).collect(Collectors.toList());
    }
}
