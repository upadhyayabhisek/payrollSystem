import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.sql.*;

public class attendancePage implements ActionListener, MouseListener {
    JFrame f = new JFrame();
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    JLabel crossButton;
    Icon crossFill = new ImageIcon("crossFill.png");
    JPanel colorPanel,tablePanel;
    JButton homeLogo,importButton,fineButton;
    JTable table;
    Color color = new Color(45, 93, 168);
    Icon homePageLogo = new ImageIcon("homePageLogo.png");
    Connection con=databaseConnection.getConnection();
    Icon importIcon=new ImageIcon("button_import.png");
    Icon fineIcon=new ImageIcon("button_fine.png");

    attendancePage(){
        addAttendancePageComponents();
        displayAttendanceData();

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

    public void displayAttendanceData(){
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Employee ID", "Employee Name", "Total Present Days", "Total Absent Days"}, 0);
        table.setModel(model);
        table.setFont(new Font("Arial", 3, 13));
        table.setEnabled(false);
        tablePanel = new JPanel();
        try {
            String query = "select e.id,e.name,\n" +
                    "sum(case when a.status='Present' then 1 else 0 end) as total_present,\n" +
                    "sum(case when a.status='Absent' then 1 else 0 end) as total_absent\n" +
                    "from employee e\n" +
                    "left join attendance a on e.id=a.emp_id\n" +
                    "GROUP by e.id,e.name";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int employeeId = rs.getInt("id");
                String employeeName = rs.getString("name");
                String totalPresent = rs.getString("total_present");
                String totalAbsent = rs.getString("total_absent");
                if(Integer.parseInt(totalAbsent)%5==0&& Integer.parseInt(totalAbsent)!=0){
                    //System.out.println(employeeName);
                    employeeName = "<html><font color='red'>" + employeeName + "</font></html>";
                    salaryFinePointSet(employeeId);
                }
                model.addRow(new Object[]{employeeId, employeeName, totalPresent, totalAbsent});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(220, 200, 760, 470);
        f.add(scrollPane);

        changeColumnWidth(0, 10);
        changeColumnWidth(1, 75);
        changeColumnWidth(2, 35);
        changeColumnWidth(3, 35);
    }

    public void salaryFinePointSet(int employeeId){
        //System.out.println(employeeId);
        String sqlFine="UPDATE attendance SET finepoint = 1 WHERE emp_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sqlFine);
            pstmt.setInt(1,employeeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAttendancePageComponents() {
        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);

        JLabel displayLabel = new JLabel("Attendance:");
        displayLabel.setFont(new Font("Calibri", Font.BOLD, 50));
        displayLabel.setBounds(220, 50, 400, 50);
        f.add(displayLabel);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        importButton=new JButton("import");
        importButton.setFont(new Font("Arial", 2, 0));
        importButton.setBounds(220, 120, 149, 60);
        importButton.setIcon(importIcon);
        importButton.addActionListener(this);
        importButton.setBackground(color);
        importButton.setHorizontalTextPosition(JButton.CENTER);
        importButton.setVerticalTextPosition(JButton.CENTER);

        fineButton=new JButton("fine");
        fineButton.setFont(new Font("Arial", 2, 0));
        fineButton.setBounds(390, 120, 149, 60);
        fineButton.setIcon(fineIcon);
        fineButton.setBackground(color);
        fineButton.addActionListener(this);
        fineButton.setHorizontalTextPosition(JButton.CENTER);
        fineButton.setVerticalTextPosition(JButton.CENTER);

        f.add(crossButton);
        f.add(colorPanel);
        f.add(homeLogo);
        f.add(importButton);
        f.add(fineButton);
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

        if(cmd.equals("import")){
            System.out.println("import");
            JFileChooser fileChooser=new JFileChooser();
            int result=fileChooser.showOpenDialog(null);
            if(result==JFileChooser.APPROVE_OPTION){
                File selectedFile=fileChooser.getSelectedFile();
                importAttendanceData(selectedFile);
            }
        }

        if(cmd.equals("fine")){
            System.out.println("Fine button clicked");
            fineButton.setEnabled(false); // Disable the button
            applyFineToEmployee();
            fineButton.setEnabled(true); // Re-enable the button
            System.out.println("Fine button re-enabled");
        }
    }

    public void applyFineToEmployee() {
        final double FINE_PERCENTAGE = 100;
        int empId=0;
        try (PreparedStatement pstmt = con.prepareStatement("SELECT emp_id FROM attendance WHERE finepoint = 1");
             ResultSet rs = pstmt.executeQuery()) {

            boolean foundData = false;
            while (rs.next()) {
                foundData = true;
                empId = rs.getInt("emp_id");
            }

            if (!foundData) {
                System.out.println("No employee with finepoint = 1 found.");
            } else {
                System.out.println("Applying fine for employee ID: " + empId);
                System.out.println("Fine application completed.");
                clearFine(empId);
                adjustSalary(empId, FINE_PERCENTAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public void clearFine(int empId) throws SQLException {
        String sqlFine = "UPDATE attendance SET finepoint = 0 WHERE emp_id = ?";
        try (PreparedStatement pstmtClear = con.prepareStatement(sqlFine)) {
            pstmtClear.setInt(1, empId);
            pstmtClear.executeUpdate();
        }
    }

    public void adjustSalary(int empId, double finePercentage) {
        String salarySQL = "SELECT salary FROM employee WHERE id = ?";
        String salaryFix = "UPDATE employee SET salary = ? WHERE id = ?";

        try (PreparedStatement prepare = con.prepareStatement(salarySQL)) {
            prepare.setInt(1, empId);
            try (ResultSet res = prepare.executeQuery()) {
                if (res.next()) {
                    int salary = res.getInt("salary");
                    int salaryWithFine = (int) (salary - finePercentage);

                    try (PreparedStatement statementFine = con.prepareStatement(salaryFix)) {
                        statementFine.setInt(1, salaryWithFine);
                        statementFine.setInt(2, empId);
                        int rowsUpdated = statementFine.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Salary updated successfully for employee " + empId);
                        } else {
                            System.out.println("Failed to update salary for employee " + empId);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error updating salary for employee " + empId + ": " + e.getMessage());
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving salary for employee " + empId + ": " + ex.getMessage());
        }
    }

    public void importAttendanceData(File file) {

        try (FileReader reader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            boolean isFirstLine = true;
            try{
                String sql = "INSERT INTO attendance (emp_id, date, status) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                while ((line = bufferedReader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] fields = line.split(",");
                    if (fields.length < 3) {
                        continue;
                    }
                    String employeeId =fields[0].trim();
                    String date = fields[1].trim();
                    String status = fields[2].trim();

                    if (isFirstLine) {
                        // Save headers to the database
                        preparedStatement.setString(1, "Employee ID");
                        preparedStatement.setString(2, "Date");
                        preparedStatement.setString(3, "Status");
                        preparedStatement.executeUpdate();
                        isFirstLine = false;
                    } else {
                        // Save attendance data to the database
                        preparedStatement.setString(1, employeeId);
                        preparedStatement.setString(2, date);
                        preparedStatement.setString(3, status);
                        preparedStatement.executeUpdate();
                    }

                }
                JOptionPane.showMessageDialog(null, "Attendance data successfully added!");
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting to the database or executing SQL query.", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file.", e);
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

    public void changeColumnWidth(int columnIndex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }
}
