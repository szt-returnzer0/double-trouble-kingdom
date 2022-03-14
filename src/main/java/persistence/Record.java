package persistence;

public record Record(String p1Name, String p2Name, int winner, int time) {

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public int getWinner() {
        return winner;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "'" + p1Name + "','" + p2Name + "'," + winner + "," + time;
    }
}
