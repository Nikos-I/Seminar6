package gb.org.views;

import gb.org.Note;
import gb.org.Notes;
import gb.org.datawork.SQLOperation;

import java.sql.SQLException;
import java.util.Scanner;

public class ViewNote {
    SQLOperation operation = new SQLOperation();
    Notes listNotes;

    public ViewNote() throws SQLException {
        listNotes = operation.readAllNotes();
    }


    public void runNotes() {
        Commands com;
        showHelp();
        while (true) {
            try {
                String command = prompt("Введите команду: ");
                com = Commands.valueOf(command.toUpperCase());
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE -> create();
                    case READ -> read();
                    case UPDATE -> update();
                    case LIST -> list();
                    case HELP -> showHelp();
                    case DELETE -> delete();
                }
            } catch (Exception ex) {
                System.out.println("Неверный ввод");
            }
        }
    }

    private void read() throws Exception {
        listNotes = operation.readAllNotes();
        String id = prompt("Идентификатор заметки: ");
        Note dispNote = listNotes.readNote(id);
        System.out.print(dispNote.toString());
    }

    private void update() throws Exception {
        String idNote = prompt("Идентификатор пользователя: ");
        String field_name = prompt("Какое поле изменить (NAME,TEXT): ").toUpperCase();
        String param = prompt("Введите новое значение: ");
        Note dispNote = listNotes.readNote(idNote);
        Fields fld = Fields.valueOf(String.valueOf(Fields.valueOf(field_name.toUpperCase())));
        Note finalDispNote = dispNote.editNote(dispNote, fld, param);
        listNotes.delNote(finalDispNote.getId());
        listNotes.notes.add(finalDispNote);
        operation.updateNote(finalDispNote);

    }

    private void delete() {
        String userid = prompt("Идентификатор заметки: ");
        listNotes.delNote(userid);
        operation.delNote(userid);
    }

    private void list() {
        listNotes = operation.readAllNotes();
        listNotes.listNotes();
    }

    private void create() {
        int nextNote = 0;
        for (Note n : listNotes.notes) {
            if (n.getIdInt() > nextNote) {
                nextNote = n.getIdInt();
            }
        }
        String idNote = String.valueOf(nextNote + 1);
        String nameNote = prompt("Название: ");
        String textNote = prompt("Текст заметки: ");
        Note newNote = new Note(idNote, nameNote, textNote);
//        listNotes.addNote(newNote);
        operation.addNote(newNote);
        listNotes = operation.readAllNotes();
    }


    private void showHelp() {
        System.out.println("Список команд:");
        for (Commands c : Commands.values()) {
            System.out.println(c);
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
