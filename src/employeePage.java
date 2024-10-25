import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeePage implements MouseListener, KeyListener, ActionListener {
    JFrame f = new JFrame();
    JLabel crossButton, searchLabel;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    JPanel colorPanel;
    JTextField searchBar;
    JTable employeeTable;
    JButton refreshTableButton, homeLogo, addUserButton, deleteUserButton;
    Color color = new Color(45, 93, 168);
    Icon refresh = new ImageIcon("refresh.png");
    Icon homePageLogo = new ImageIcon("homePageLogo.png");
    Icon addUser = new ImageIcon("addUser.png");
    Icon deleteUser = new ImageIcon("deleteUser.png");

    employeePage() {
        addemployeePageComponents();

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

    public void addemployeePageComponents() {
        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);

        JLabel displayLabel = new JLabel("Employee:");
        displayLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        displayLabel.setBounds(220, 30, 400, 50);

        f.add(displayLabel);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        searchBar = new JTextField();
        searchBar.setBounds(320, 90, 500, 43);
        searchBar.addKeyListener(this);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", 3, 18));
        searchLabel.setBounds(220, 90, 100, 45);

        refreshTableButton = new JButton("refresh");
        refreshTableButton.setBackground(color);
        refreshTableButton.setFont(new Font("Arial", 2, 0));
        refreshTableButton.setBounds(830, 90, 41, 42);
        refreshTableButton.setIcon(refresh);
        refreshTableButton.addActionListener(this);
        refreshTableButton.setHorizontalTextPosition(JLabel.CENTER);
        refreshTableButton.setVerticalTextPosition(JLabel.CENTER);

        addUserButton = new JButton("add");
        addUserButton.setFont(new Font("Arial", 2, 0));
        addUserButton.setBounds(320, 145, 100, 40);
        addUserButton.setIcon(addUser);
        addUserButton.addActionListener(this);
        addUserButton.setBackground(color);
        addUserButton.setHorizontalTextPosition(JLabel.CENTER);
        addUserButton.setVerticalTextPosition(JLabel.CENTER);

        deleteUserButton = new JButton("delete");
        deleteUserButton.setFont(new Font("Arial", 2, 0));
        deleteUserButton.setBounds(445, 145, 100, 40);
        deleteUserButton.setIcon(deleteUser);
        deleteUserButton.setBackground(color);
        deleteUserButton.addActionListener(this);
        deleteUserButton.setHorizontalTextPosition(JLabel.CENTER);
        deleteUserButton.setVerticalTextPosition(JLabel.CENTER);

        String[] columnData = {"Id", "Name", "Email", "Phone", "Address", "Salary"};
        Object[][] rowData = new Object[0][columnData.length];

        DefaultTableModel model = new DefaultTableModel(rowData, columnData);

        employeeTable = new JTable(model);
        employeeTable.setFont(new Font("Arial", 3, 13));
        employeeTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(220, 200, 760, 470);
        scrollPane.setBackground(Color.white);

        setRecordToTable();

        f.add(homeLogo);
        f.add(colorPanel);
        f.add(crossButton);
        f.add(scrollPane);
        f.add(searchBar);
        f.add(searchLabel);
        f.add(refreshTableButton);
        f.add(addUserButton);
        f.add(deleteUserButton);

        changeColumnWidth(0, 10);
        changeColumnWidth(1, 50);
        changeColumnWidth(2, 130);
        changeColumnWidth(3, 25);
        changeColumnWidth(4, 30);
        changeColumnWidth(5, 20);

    }

    public void setRecordToTable() {
        try {
            Connection con = databaseConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String salary = rs.getString("salary");
                Object[] obj = {id, name, email, phone, address, salary};
                DefaultTableModel modelTest = (DefaultTableModel) employeeTable.getModel();
                modelTest.addRow(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchData(String str) {
        DefaultTableModel modelSearch = (DefaultTableModel) employeeTable.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelSearch);
        employeeTable.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }

    public void changeColumnWidth(int columnIndex, int width) {
        TableColumn column = employeeTable.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }

    public void removeAllRows() {
        DefaultTableModel removeModel = (DefaultTableModel) employeeTable.getModel();
        removeModel.setRowCount(0);
        setRecordToTable();
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String searchString = searchBar.getText();
        searchData(searchString);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("refresh")) {
            System.out.println("refresh");
            removeAllRows();
        }

        if (cmd.equals("home")) {
            System.out.println("home");
            new homePage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("add")) {
            System.out.println("add");
            new addUserPage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("delete")) {
            System.out.println("delete");
            deleteUserFromDatabase();
        }
    }

    public void deleteUserFromDatabase() {
        String idInput = JOptionPane.showInputDialog(f, "Enter ID to delete");
        int id = (Integer.parseInt(idInput));
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "delete from employee where id=?";
            String sql1 = "delete from tax where empid=?";
            String sql3="delete from attendance where emp_id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            PreparedStatement statemen1 = con.prepareStatement(sql1);
            PreparedStatement statement3= con.prepareStatement(sql3);
            statement.setInt(1, id);
            statemen1.setInt(1, id);
            statement3.setInt(1,id);
            statemen1.executeUpdate();
            statement3.executeUpdate();
            statement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        removeAllRows();
    }
}
