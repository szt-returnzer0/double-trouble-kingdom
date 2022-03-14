package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Record {
    private String p1Name;
    private String p2Name;
    private int winner;
    private int time;
    private int id;

    public Record(String p1Name, String p2Name, int winner, int time) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.winner = winner;
        this.time = time;
    }

    public Record(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("ID");
            this.p1Name = resultSet.getString("P1NAME");
            this.p2Name = resultSet.getString("P2NAME");
            this.winner = resultSet.getInt("WINNER");
            this.time = resultSet.getInt("TIME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public int getWinner() {
        return winner;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "'" + p1Name + "','" + p2Name + "'," + winner + "," + time;
    }

    public String p1Name() {
        return p1Name;
    }

    public String p2Name() {
        return p2Name;
    }

    public int winner() {
        return winner;
    }

    public int time() {
        return time;
    }
}
