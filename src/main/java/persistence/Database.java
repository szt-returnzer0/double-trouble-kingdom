package persistence;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Database implements Serializable {
    private Connection c;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
            ResultSet tables = c.getMetaData().getTables(null, null, "RECORDS", null);
            if (!tables.next()) createTable();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

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

    public void saveRecord(Record record) {
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

    public ArrayList<Record> getRecords() {
        ArrayList<Record> resultArray = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM RECORDS";
            ResultSet results = stmt.executeQuery(sql);
            while (results.next())
                resultArray.add(new Record(results));
            System.out.println(results);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    public void closeConnection() {
        try {
            c.close();
            c = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
