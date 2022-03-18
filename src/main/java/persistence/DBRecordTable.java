package persistence;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DBRecordTable extends AbstractTableModel {

    private final ArrayList<DBRecord> records;

    private final String[] colName = new String[]{"1-es játékos", "2-es játékos", "Kimenetel", "Eltelt idő"};


    public DBRecordTable(ArrayList<DBRecord> records) {
        this.records = records;
    }


    @Override
    public int getRowCount() {
        return records.size();
    }


    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        DBRecord rec = records.get(r);
        if (c == 0) return rec.p1Name();
        else if (c == 1) return rec.p2Name();
        else if (c == 2) return rec.getWinner();
        else return rec.getTime();
    }

    @Override
    public String getColumnName(int i) {
        return colName[i];
    }
}
