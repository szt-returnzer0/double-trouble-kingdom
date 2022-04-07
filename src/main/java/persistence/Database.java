package persistence;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of the Game's Database class, contains SQL queries, and SQLite connection opening and closing methods.
 */
public class Database {
    /**
     * Connection to the SQLite database.
     */
    private Connection c;

    /**
     * Constructs the Database class. Opens a new SQLite connection, creates Game.db if it does not exist.
     */
    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
            ResultSet tables = c.getMetaData().getTables(null, null, "RECORDS", null);
            if (!tables.next()) createTable();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * SQL query to crate the Game.db database.
     */
    public void createTable() {
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE RECORDS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " P1NAME TEXT NOT NULL," +
                    " P2NAME TEXT NOT NULL," +
                    " WINNER INT NOT NULL," +
                    " TIME INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
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
            Statement stmt = c.createStatement();
            String sql = "INSERT INTO RECORDS (P1NAME,P2NAME,WINNER,TIME) " +
                    "VALUES (" + record.toString() + ")";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to delete a record by id.
     *
     * @param id the id of the record we want to delete
     */
    public void deleteRecordByID(int id) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM RECORDS WHERE ID=" + id;
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to delete all the records in the database.
     */
    public void deleteAllRecords() {
        try {
            Statement stmt = c.createStatement();
            String sql = "DELETE FROM RECORDS";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL query to select all the records, and returns it as an ArrayList containing DBRecords.
     *
     * @return an ArrayList containing DBRecords
     */
    public ArrayList<DBRecord> getRecords() {
        ArrayList<DBRecord> resultArray = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM RECORDS";
            ResultSet results = stmt.executeQuery(sql);
            while (results.next())
                resultArray.add(new DBRecord(results));
            System.out.println(results);
            stmt.close();
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
            c.close();
            c = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Database connection.
     */
    public void openConnection() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
