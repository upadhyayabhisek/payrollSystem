import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;

public class analysisPage implements ActionListener, MouseListener {
    JFrame f = new JFrame();
    JLabel crossButton, titlePageMain;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Icon homePageLogo = new ImageIcon("homePageLogo.png");
    Icon analysisLogo = new ImageIcon("analysisLogo.png");
    JPanel colorPanel;
    JButton homeLogo, refreshTableButton;
    Color color = new Color(45, 93, 168);
    JPanel totalEmployeePanel, totalEmployeeExpensePanel, totalEmployeeTaxPanel, totalEmployeePFPanel;
    Icon refresh = new ImageIcon("refresh.png");
    JTable table;
    DefaultTableModel model;

    Connection con = databaseConnection.getConnection();

    analysisPage() {
        analysisPageComponents();
        displayTotalEmployee();
        displayTotalSalaryExpence();
        displayTotalTaxWitheld();
        displayTotalPFContribution();
        employeeTable();

        f.getContentPane().setBackground(Color.white);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setUndecorated(true);
        f.setResizable(false);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public void displayTotalTaxWitheld() {
        totalEmployeeTaxPanel = new JPanel();
        totalEmployeeTaxPanel.setBackground(Color.lightGray);
        totalEmployeeTaxPanel.setBounds(250, 280, 300, 120);
        try {
            Statement statement = con.createStatement();
            String sql = "select sum(tax) as totalemployeetax from tax";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                float totalEmployeeTaxCount = rs.getFloat("totalemployeetax");
                JLabel totalTaxCount = new JLabel("<html>Tax Withheld:<br>" + "<center>" + NumberFormat.getInstance(Locale.US).format(totalEmployeeTaxCount));
                totalTaxCount.setFont(new Font("Arial", 3, 35));
                System.out.println(totalEmployeeTaxCount);
                totalEmployeeTaxPanel.add(totalTaxCount);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(totalEmployeeTaxPanel);
    }

    public void displayTotalEmployee() {
        totalEmployeePanel = new JPanel();
        totalEmployeePanel.setBackground(Color.lightGray);
        totalEmployeePanel.setBounds(250, 150, 300, 120);
        try {
            Statement statement = con.createStatement();
            String sql = "select count(*) as totalemployee from employee";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                int totalEmployeeCount = rs.getInt("totalemployee");
                JLabel totalCount = new JLabel("<html>Total Employee:<br>" + "<center>" + totalEmployeeCount);
                totalCount.setFont(new Font("Arial", 3, 35));
                System.out.println(totalEmployeeCount);
                totalEmployeePanel.add(totalCount);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(totalEmployeePanel);
    }

    public void displayTotalSalaryExpence() {
        totalEmployeeExpensePanel = new JPanel();
        totalEmployeeExpensePanel.setBackground(Color.lightGray);
        totalEmployeeExpensePanel.setBounds(250, 410, 300, 120);
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT \n" +
                    "    (SELECT SUM(salary) FROM employee) AS total_salary,\n" +
                    "    (SELECT SUM(allowance) FROM tax) AS total_allowance,\n" +
                    "    (SELECT SUM(bonus) FROM tax) AS total_bonus;\n";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {

                float totalSalary=rs.getFloat("total_salary");
                float totalAllowance=rs.getFloat("total_allowance");
                float totalBonus=rs.getFloat("total_bonus");
                float totalEmployeeExpense = totalSalary+totalBonus+totalAllowance;
                JLabel totalExpense = new JLabel("<html>Salary Expenses:<br>" + "<center>" + NumberFormat.getInstance(Locale.US).format(totalEmployeeExpense));
                totalExpense.setFont(new Font("Arial", 3, 35));
                System.out.println(totalEmployeeExpense);
                totalEmployeeExpensePanel.add(totalExpense);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(totalEmployeeExpensePanel);
    }

    public void displayTotalPFContribution() {
        totalEmployeePFPanel = new JPanel();
        totalEmployeePFPanel.setBackground(Color.lightGray);
        totalEmployeePFPanel.setBounds(250, 540, 300, 130);
        try {
            Statement statement = con.createStatement();
            String sql = "select sum(provident_fund) as totalpfexpense from tax";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                float totalEmployeePFExpense = rs.getFloat("totalpfexpense");
                JLabel totalPFExpense = new JLabel("<html>Employer PF<br>contribution:<br>" + "<center>" + NumberFormat.getInstance(Locale.US).format(totalEmployeePFExpense));
                totalPFExpense.setFont(new Font("Arial", 3, 35));
                System.out.println(totalEmployeePFExpense);
                totalEmployeePFPanel.add(totalPFExpense);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(totalEmployeePFPanel);
    }

    public void analysisPageComponents() {
        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);

        JLabel analysisGraphicLogo=new JLabel();
        analysisGraphicLogo.setIcon(analysisLogo);
        analysisGraphicLogo.setBounds(650,420,250,250);
        analysisGraphicLogo.setHorizontalTextPosition(JLabel.CENTER);
        analysisGraphicLogo.setVerticalTextPosition(JLabel.CENTER);
        f.add(analysisGraphicLogo);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        refreshTableButton = new JButton("refresh");
        refreshTableButton.setBackground(color);
        refreshTableButton.setFont(new Font("Arial", 2, 0));
        refreshTableButton.setBounds(200, 0, 41, 42);
        refreshTableButton.setIcon(refresh);
        refreshTableButton.addActionListener(this);
        refreshTableButton.setHorizontalTextPosition(JLabel.CENTER);
        refreshTableButton.setVerticalTextPosition(JLabel.CENTER);

        titlePageMain = new JLabel("Total Analysis:");
        titlePageMain.setFont(new Font("Calibri", Font.BOLD, 50));
        titlePageMain.setBounds(250, 70, 400, 50);

        f.add(refreshTableButton);
        f.add(titlePageMain);
        f.add(homeLogo);
        f.add(crossButton);
        f.add(colorPanel);
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

        if (cmd.equals("refresh")) {
            refreshAllTab();
        }
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

    public void refreshAllTab() {
        totalEmployeePanel.removeAll();
        totalEmployeeExpensePanel.removeAll();
        totalEmployeeTaxPanel.removeAll();
        totalEmployeePFPanel.removeAll();
        displayTotalTaxWitheld();
        displayTotalSalaryExpence();
        displayTotalEmployee();
        displayTotalPFContribution();
        removeAllRows();
        totalEmployeePanel.revalidate();
        totalEmployeePanel.repaint();
    }

    public void employeeTable() {
        String[] columns = {"Address", "Number of Employees"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(600, 150, 350, 250);
        scrollPane.setBackground(Color.white);
        f.add(scrollPane);
        changeColumnWidth(0, 70);
        changeColumnWidth(1, 50);
        fetchDataFromDatabase();
    }

    public void fetchDataFromDatabase() {
        try {
            String sql = "SELECT address, COUNT(*) AS num_employees FROM employee GROUP BY address ORDER BY num_employees DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String address = rs.getString("address");
                int numEmployees = rs.getInt("num_employees");
                model.addRow(new Object[]{address, numEmployees});
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void removeAllRows() {
        DefaultTableModel removeModel = (DefaultTableModel) table.getModel();
        removeModel.setRowCount(0);
        fetchDataFromDatabase();
    }

    public void changeColumnWidth(int columnIndex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }
}
