package uk.ac.ucl.model;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
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

    public void addNote(Note note) {
        notes.add(note);
        saveNotes();
    }

    public List<Note> getNotes() {
        return notes;
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
                notes = mapper.readValue(file, new TypeReference<List<Note>>() {});
            } else {
                notes = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
