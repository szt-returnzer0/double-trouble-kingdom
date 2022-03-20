package view;

import persistence.DBRecord;
import persistence.DBRecordTable;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Implements a dialog to show Database query records.
 */
public class HallOfFame extends JDialog {
    /**
     * Constructs a new Hall of Fame JDialog instance
     *
     * @param records the records we want to show in an ArrayList
     * @param parent  the parent frame of the dialog
     */
    public HallOfFame(ArrayList<DBRecord> records, JFrame parent) {
        super(parent, true);
        JTable table = new JTable(new DBRecordTable(records));
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        setSize(400, 400);
        setTitle("Dicsőség tábla");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
