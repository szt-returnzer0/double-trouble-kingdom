package view;

import persistence.DBRecord;
import persistence.DBRecordTable;

import javax.swing.*;
import java.util.ArrayList;

public class HallOfFame extends JDialog {
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
