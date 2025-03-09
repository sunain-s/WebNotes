package uk.ac.ucl.model;

import java.util.List;

public class NoteManagerTest {
    public static void main(String[] args) {
        // 1. Get the singleton instance of NoteManager
        NoteManager noteManager = NoteManager.getInstance();

        try {
            // 2. Create some valid notes to add
            Note note1 = new Note("Meeting Notes", "Discuss project milestones", null, null, "Work");
            Note note2 = new Note("Reference", null, "https://example.com", null, "Study");
            Note note3 = new Note("Personal", null, null, "https://image.example.com", "Life");

            // 3. Add valid notes using NoteManager
            noteManager.addNote(note1);
            noteManager.addNote(note2);
            noteManager.addNote(note3);

            // 4. Retrieve and display the list of notes
            List<Note> notes = noteManager.getNotes();
            System.out.println("Notes in the system:");
            for (Note note : notes) {
                System.out.println("Title: " + note.getTitle() + ", Category: " + note.getCategory());
                if (note.getText() != null) {
                    System.out.println("Text: " + note.getText());
                }
                if (note.getUrl() != null) {
                    System.out.println("URL: " + note.getUrl());
                }
                if (note.getImgUrl() != null) {
                    System.out.println("Image URL: " + note.getImgUrl());
                }
                System.out.println("---");
            }

            // 5. Try creating an invalid note that should throw an exception
            try {
                // This should throw an IllegalArgumentException due to missing content (text/url/image)
                Note invalidNote = new Note("Invalid Note", null, null, null, "General");
                System.out.println("Invalid Note created: " + invalidNote.getTitle());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage()); // Expected: "Note content (text/url/image) is required"
            }

            // 6. Simulate loading and saving notes (it will persist them in the file)
            NoteManager anotherNoteManager = NoteManager.getInstance();
            List<Note> loadedNotes = anotherNoteManager.getNotes();
            System.out.println("Loaded Notes after persistence:");
            for (Note loadedNote : loadedNotes) {
                System.out.println("Title: " + loadedNote.getTitle() + ", Category: " + loadedNote.getCategory());
                if (loadedNote.getText() != null) {
                    System.out.println("Text: " + loadedNote.getText());
                }
                if (loadedNote.getUrl() != null) {
                    System.out.println("URL: " + loadedNote.getUrl());
                }
                if (loadedNote.getImgUrl() != null) {
                    System.out.println("Image URL: " + loadedNote.getImgUrl());
                }
                System.out.println("---");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
