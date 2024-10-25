import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class reportPage implements MouseListener, ActionListener {

    JFrame f = new JFrame();

    JButton homeLogo, download;
    JLabel crossButton;
    JPanel colorPanel, tablePanel;
    JTable table;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Icon downloadImage = new ImageIcon("download.png");
    Connection con = databaseConnection.getConnection();

    reportPage() {
        Color color = new Color(45, 93, 168);

        Icon homePageLogo = new ImageIcon("homePageLogo.png");

        JLabel displayLabel = new JLabel("Report:");
        displayLabel.setFont(new Font("Calibri", Font.BOLD, 50));
        displayLabel.setBounds(220, 50, 400, 50);

        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        download = new JButton("download");
        download.setFont(new Font("Arial", 2, 0));
        download.setBounds(220, 120, 149, 60);
        download.setIcon(downloadImage);
        download.addActionListener(this);
        download.setHorizontalTextPosition(JButton.CENTER);
        download.setVerticalTextPosition(JButton.CENTER);

        displayData();

        f.add(homeLogo);
        f.add(crossButton);
        f.add(colorPanel);
        f.add(download);
        f.add(displayLabel);
        f.getContentPane().setBackground(Color.WHITE);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setUndecorated(true);
        f.setResizable(false);
        f.setUndecorated(true);
        f.setVisible(true);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        f.dispose();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        crossButton.setIcon(crossFill);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        crossButton.setIcon(crossEmpty);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("home")) {
            System.out.println("home");
            new homePage();
            f.setVisible(false);
            f.dispose();
        }
        if (cmd.equals("download")) {
            System.out.println("download");
            saveTableToFile(table);
        }
    }

    public void displayData() {
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "NAME", "SALARY", "TAX", "PROVIDENT", "INSURANCE", "SS"}, 0);
        table.setModel(model);
        table.setFont(new Font("Arial", 3, 13));
        table.setEnabled(false);
        tablePanel = new JPanel();
        try {
            String query = "SELECT e.id, e.name,e.salary, t.tax,t.provident_fund,t.insurance,t.socialsecurity from employee e inner JOIN tax t on e.id=t.empid;";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String salary = rs.getString("salary");
                String tax = rs.getString("tax");
                String provident = rs.getString("provident_fund");
                String insurance = rs.getString("insurance");
                String social = rs.getString("socialsecurity");
                model.addRow(new Object[]{id, name, salary, tax, provident, insurance, social});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(220, 200, 760, 470);
        f.add(scrollPane);

        changeColumnWidth(0, 10);
        changeColumnWidth(1, 100);
        changeColumnWidth(2, 50);
        changeColumnWidth(3, 50);
        changeColumnWidth(4, 50);
        changeColumnWidth(5, 50);
    }

    public void changeColumnWidth(int columnIndex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }

    public void saveTableToFile(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("choose file");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                for (int i = 0; i < table.getColumnCount(); i++) {
                    writer.write(table.getColumnName(i) + (i < table.getColumnCount() - 1 ? "," : ""));
                }
                writer.write("\n");
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        writer.write(table.getValueAt(i, j).toString() + (j < table.getColumnCount() - 1 ? "," : ""));

                    }
                    writer.write("\n");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
