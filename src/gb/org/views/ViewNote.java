package gb.org.views;

import gb.org.Note;
import gb.org.Notes;
import gb.org.fileoperation.SQLOperation;

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
//                    case UPDATE -> update();
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
        String id = prompt("Идентификатор заметки: ");
        Note dispNote = listNotes.readNote(id);
        System.out.print(dispNote.toString());
    }

//    private void update() throws Exception {
//        String userid = prompt("Идентификатор пользователя: ");
//        String field_name = prompt("Какое поле изменить (FIO,NAME,TELEPHONE): ").toUpperCase();
//        String param = null;
//        if (Fields.valueOf(field_name) == Fields.TELEPHONE) {
//            param = catchTelephone(param);
//            if (param == null) {
//                return;
//            }
//        } else {
//            param = prompt("Введите новое значение: ");
//        }
//        User _user = userController.readUser(userid);
//        userController.updateUser(_user, Fields.valueOf(field_name.toUpperCase()), param);
//    }

    private void delete(){
        String userid = prompt("Идентификатор заметки: ");
        listNotes.delNote(userid);
    }

    private void list() throws Exception {
        listNotes.listNotes();
    }

    private void create() {
        String idNote = String.valueOf(Integer.parseInt(listNotes.notes.get(listNotes.notes.size() - 1).getId())+1);
        String nameNote = prompt("Название: ");
        String textNote = prompt("Текст заметки: ");
        Note newNote = new Note(idNote, nameNote, textNote);
        listNotes.addNote(newNote);
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
