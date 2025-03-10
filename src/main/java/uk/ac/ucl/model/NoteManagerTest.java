package uk.ac.ucl.model;

import java.util.List;

public class NoteManagerTest {
    public static void main(String[] args) {
        // 1. Get the singleton instance of NoteManager
        NoteManager noteManager = NoteManager.getInstance();

        // 2. Clear any existing notes for a clean test
        noteManager.getNotes().clear();

        // 3. Create some notes
        Note note1 = new Note("Meeting Notes", "Discuss project milestones", null, null, "Work");
        Note note2 = new Note("Reference", null, "https://example.com", null, "Study");
        Note note3 = new Note("Personal Journal", "Reflections on the day", null, null, "Life");

        // 4. Add notes to the manager
        System.out.println("Adding notes...");
        noteManager.addNote(note1);
        noteManager.addNote(note2);
        noteManager.addNote(note3);

        // 5. Retrieve and display all notes
        System.out.println("\nAll Notes:");
        List<Note> notes = noteManager.getNotes();
        for (Note note : notes) {
            System.out.println("Title: " + note.getTitle() + ", Category: " + note.getCategory());
        }

        // 6. Retrieve a specific note by title
        System.out.println("\nRetrieving 'Meeting Notes'...");
        Note retrievedNote = noteManager.getNoteByTitle("Meeting Notes");
        if (retrievedNote != null) {
            System.out.println("Found Note: " + retrievedNote.getTitle() + " - " + retrievedNote.getText());
        } else {
            System.out.println("Note not found.");
        }

        // 7. Edit a note
        System.out.println("\nEditing 'Meeting Notes'...");
        noteManager.editNote("Meeting Notes", "Updated Meeting Notes", "Updated discussion points", null, "Work");
        Note updatedNote = noteManager.getNoteByTitle("Updated Meeting Notes");
        if (updatedNote != null) {
            System.out.println("Updated Note: " + updatedNote.getTitle() + " - " + updatedNote.getText());
        }

        // 8. Search for notes containing a keyword
        System.out.println("\nSearching for 'Reference'...");
        List<Note> searchResults = noteManager.searchNotes("Reference");
        for (Note searchNote : searchResults) {
            System.out.println("Found: " + searchNote.getTitle());
        }

        // 9. Delete a note
        System.out.println("\nDeleting 'Updated Meeting Notes'...");
        noteManager.deleteNote("Updated Meeting Notes");
        Note deletedNote = noteManager.getNoteByTitle("Updated Meeting Notes");
        System.out.println(deletedNote == null ? "Note successfully deleted!" : "Deletion failed.");

        // 10. Display final notes after deletion
        System.out.println("\nFinal Notes List:");
        notes = noteManager.getNotes();
        for (Note note : notes) {
            System.out.println("Title: " + note.getTitle());
        }
    }
}
