package gb.org.datawork;

import gb.org.Note;
import gb.org.Notes;

public interface Operation {
    public int addNote(Note note);

    public Note displayNote(String id);

    public void updateNote(Note note);

    public void delNote(String id);

    public Notes readAllNotes();
}
