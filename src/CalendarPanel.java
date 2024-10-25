import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

public class CalendarPanel extends JPanel {
    private final String[] years = {"2024", "2025", "2026"};
    private final JComboBox<String> comboBox = new JComboBox<>(years);
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final JComboBox<String> list = new JComboBox<>(months);
    private final CalendarModel model = new CalendarModel();
    private final JTable table = new JTable(model);

    public CalendarPanel() {
        setLayout(null);

        comboBox.setBounds(90, 120, 100, 30);
        comboBox.setSelectedIndex(0);
        comboBox.addItemListener(new ComboHandler());
        add(comboBox);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(210, 120, 100, 32);
        list.setSelectedIndex(month);
        list.addItemListener(new ListHandler());
        add(scrollPane);

        table.setBounds(60, 170, 350, 290);
        model.setMonth(Integer.parseInt(years[0]), list.getSelectedIndex());
        table.setGridColor(Color.black);
        table.setFont(new Font("Arial", Font.BOLD, 15));
        table.setForeground(Color.decode("#000000"));
        table.setShowGrid(false);
        table.setOpaque(false);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);

        add(table, BorderLayout.CENTER);
    }

    static class CalendarModel extends AbstractTableModel {
        private final int[] numDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private final String[][] calendar = new String[7][7];

        public CalendarModel() {
            String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            System.arraycopy(days, 0, calendar[0], 0, days.length);
            for (int i = 1; i < 7; ++i)
                for (int j = 0; j < 7; ++j)
                    calendar[i][j] = " ";
        }

        public int getRowCount() {
            return 7;
        }

        public int getColumnCount() {
            return 7;
        }

        public Object getValueAt(int row, int column) {
            return calendar[row][column];
        }

        public void setValueAt(Object value, int row, int column) {
            calendar[row][column] = (String) value;
        }

        public void setMonth(int year, int month) {
            for (int i = 1; i < 7; ++i)
                for (int j = 0; j < 7; ++j)
                    calendar[i][j] = " ";
            java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
            cal.set(year, month, 1);
            int offset = cal.get(java.util.GregorianCalendar.DAY_OF_WEEK) - 1;
            offset += 7;
            int num = daysInMonth(year, month);
            for (int i = 0; i < num; ++i) {
                calendar[offset / 7][offset % 7] = Integer.toString(i + 1);
                ++offset;
            }
        }

        public boolean isLeapYear(int year) {
            return year % 4 == 0;
        }

        public int daysInMonth(int year, int month) {
            int days = numDays[month];
            if (month == 1 && isLeapYear(year)) ++days;
            return days;
        }
    }

    public class ComboHandler implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            model.setMonth(Integer.parseInt(years[comboBox.getSelectedIndex()]), list.getSelectedIndex());
            table.repaint();
        }
    }

    public class ListHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            model.setMonth(Integer.parseInt(years[comboBox.getSelectedIndex()]), list.getSelectedIndex());
            table.repaint();
        }
    }
}
