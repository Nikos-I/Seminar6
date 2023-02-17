package gb.org;

import gb.org.views.Fields;

import java.lang.reflect.Field;

public class Note {

    private String idNote;
    private String nameNote;
    private String textNote;


    public Note() {
    }

    public Note(String idNote, String nameNote, String textNote) {
        this.idNote = idNote;
        this.nameNote = nameNote;
        this.textNote = textNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getId() {
        return idNote;
    }

    public int getIdInt() {
        return Integer.parseInt(idNote);
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public Note editNote(Note edtNote, Fields field, String valueField) {

        if (field == Fields.TEXT) {
            edtNote.setTextNote(valueField);
        } else if (field == Fields.NAME) {
            edtNote.setNameNote(valueField);
        }
        return edtNote;
    }

    @Override
    public String toString() {
        return "\nid: " + this.idNote + " Название: " + this.nameNote + " Текст: " + this.textNote;
    }
}