package gb.org.datawork;

import gb.org.Note;
import gb.org.Notes;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.Collections;


public class SQLOperation implements Operation {
    // адрес подключения
    private static final String CON_STR = "jdbc:sqlite:D:\\GDisk\\GeekBraims\\Java\\JavaSeminars\\OOP\\Seminar6\\src\\gb\\org\\notes.db";

    // Чтобы не плодить множество
    // экземпляров класса SQLOperation
    private static SQLOperation instance = null;
    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    public SQLOperation() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public static synchronized SQLOperation getInstance() throws SQLException {
        if (instance == null)
            instance = new SQLOperation();
        return instance;
    }

    @Override
    public int addNote(Note note) {
        // Подготовим выражение
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Notes (`idNote`, `nameNote`, `textNote`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, note.getId());
            statement.setObject(2, note.getNameNote());
            statement.setObject(3, note.getTextNote());

            // Выполним запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Note displayNote(String id) {
        Note note = null;

        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT idNote, nameNote, textNote FROM Notes WHERE idNote = ?")) {
            statement.setObject(1, id);
            // В resultSet результат нашего запроса,
            ResultSet resultSet = statement.executeQuery();
            // Заносим данные в note
            note.setIdNote(resultSet.getString("idNote"));
            note.setNameNote(resultSet.getString("nameNote"));
            note.setTextNote(resultSet.getString("textNote"));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        // Возвращаем наш список
        return note;
    }


    @Override
    public void editNote(String id) {

    }

    @Override
    public void delNote(String id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM notes WHERE idNote = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notes readAllNotes() {
        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            Notes notes = new Notes();
            // В resultSet результат нашего запроса,
            ResultSet resultSet = statement.executeQuery("SELECT idNote, nameNote, textNote FROM notes");
            // Проходимся по нашему resultSet и заносим данные в notes
            while (resultSet.next()) {
                Note newNote = new Note();
                newNote.setIdNote(resultSet.getString("idNote"));
                newNote.setNameNote(resultSet.getString("nameNote"));
                newNote.setTextNote(resultSet.getString("textNote"));

                notes.addNote(newNote);
            }
            // Возвращаем наш список
            return notes;
        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return (Notes) Collections.emptyList();
        }
    }
}
