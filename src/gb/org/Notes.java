package gb.org;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    public List<Note> notes = new ArrayList<>();

    public Notes() {
    }

    public Notes(List<Note> notes) {
        this.notes = notes;
    }

    //    Возвращает заметку по её Id
    public Note readNote(String noteId) throws Exception {
        for (Note note : notes) {
            if (noteId.equals(note.getId())) {
                return note;
            }
        }
        throw new Exception("Заметка не найдена!");
    }

    //    Удаляет заметку по её Id
    public void delNote(String noteId) {
        notes.removeIf(note -> noteId.equals(note.getId()));
    }

    //Вывод списка заметок
    public void listNotes() {
        for (Note note : notes) {
            System.out.print(note);
        }
        System.out.print("\n");
    }

    //    Добавление заметки в список
    public void addNote(Note note) {
        notes.add(note);
    }
}


