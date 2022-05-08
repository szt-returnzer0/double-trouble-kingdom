package DTK_persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of the Game's Database class, contains SQL queries, and SQLite
 * connection opening and closing methods.
 */
public class Database implements Serializable {
    Statement statement;
    String query;
    /**
     * Connection to the SQLite database.
     */
    private Connection connection;

    /**
     * Constructs the Database class. Opens a new SQLite connection, creates Game.db
     * if it does not exist.
     */
    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
            ResultSet tables = connection.getMetaData().getTables(null, null, "RECORDS", null);
            if (!tables.next())
                createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to crate the Game.db database.
     */
    public void createTable() {
        try {
            statement = connection.createStatement();
            query = "CREATE TABLE RECORDS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " P1NAME TEXT NOT NULL," +
                    " P2NAME TEXT NOT NULL," +
                    " WINNER INT NOT NULL," +
                    " TIME INT NOT NULL)";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to save a DBRecord to the Database.
     *
     * @param record to save
     */
    public void saveRecord(DBRecord record) {
        try {
            statement = connection.createStatement();
            query = "INSERT INTO RECORDS (P1NAME,P2NAME,WINNER,TIME) " +
                    "VALUES (" + record.toString() + ")";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to select all the records, and returns it as an ArrayList
     * containing DBRecords.
     *
     * @return an ArrayList containing DBRecords
     */
    public ArrayList<DBRecord> getRecords() {
        ArrayList<DBRecord> resultArray = new ArrayList<>();
        try {
            statement = connection.createStatement();
            query = "SELECT * FROM RECORDS";
            ResultSet results = statement.executeQuery(query);
            while (results.next())
                resultArray.add(new DBRecord(results));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    /**
     * Closes the Database connection.
     */
    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Database connection.
     */
    public void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:game.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
