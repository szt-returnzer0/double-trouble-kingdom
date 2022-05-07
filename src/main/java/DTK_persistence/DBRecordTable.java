package DTK_persistence;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Implementation of DBRecordTable class, which converts an ArrayList of DBRecords to a TableModel.
 */
public class DBRecordTable extends AbstractTableModel {

    /**
     * An ArrayList containing the records of the table.
     */
    private final ArrayList<DBRecord> records;

    /**
     * The column names of the table.
     */
    private final String[] colName = new String[]{"1-es játékos", "2-es játékos", "Kimenetel", "Eltelt idő"};


    /**
     * Constructs a new TableModel from the passed records.
     *
     * @param records the ArrayList containing the records
     */
    public DBRecordTable(ArrayList<DBRecord> records) {
        this.records = records;
    }

    /**
     * Returns the count of rows in the table.
     *
     * @return the count of rows in the table
     */
    @Override
    public int getRowCount() {
        return records.size();
    }

    /**
     * Returns the count of columns in the table.
     *
     * @return the count of columns in the table
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Returns a value from the DBRecords at the passed row and column indexes.
     *
     * @param r the row's index
     * @param c the col's index
     * @return the value at the given indexes
     */
    @Override
    public Object getValueAt(int r, int c) {
        DBRecord rec = records.get(r);
        if (c == 0) return rec.getP1Name();
        else if (c == 1) return rec.getP2Name();
        else if (c == 2) return rec.getWinner();
        else return rec.getTime();
    }

    /**
     * Returns the table's column name at the passed column index.
     *
     * @param i the column's index
     * @return the table's column name at the passed column index
     */
    @Override
    public String getColumnName(int i) {
        return colName[i];
    }
}
