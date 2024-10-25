import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Locale;

public class employeePortalPage implements MouseListener, ActionListener {

    static Connection con = databaseConnection.getConnection();
    JFrame f = new JFrame();
    JLabel crossButton, leaveFrom, leaveTo, statusLabel, statusUpdate;
    JPanel employeeTaxPanel, employeePFPanel, employeeSocialPanel, employeeSalaryPanel, employeeInsurancePanel;
    JPanel colorPanel;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    JTextArea requestLeave;
    JButton submitDataButton, changePassword;
    JSpinner employeeYearFrom, employeeMonthFrom, employeeDayFrom, employeeYearTo, employeeMonthTo, employeeDayTo;
    Icon submit = new ImageIcon("submitdata.png");
    int idFinal;

    employeePortalPage(String name, String id) {

        this.idFinal = Integer.parseInt(id);

        Color color = new Color(45, 93, 168);
        int employeeId = Integer.parseInt(id);
        System.out.println(employeeId);


        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        JLabel welcomeLabel = new JLabel("Welcome " + name + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        welcomeLabel.setBounds(250, 30, 500, 50);

        JLabel timeLabel = new JLabel();
        timeLabel.setBounds(800, 0, 200, 30);
        updateDateTime(timeLabel);
        Timer timer = new Timer(1000, e -> updateDateTime(timeLabel));
        timer.start();


        String sql = "select password from employee where id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, employeeId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String password = result.getString("password");
                if (password.equals("12345")) {
                    JLabel changePass = new JLabel("you are using default password please change it!.");
                    changePass.setBounds(210, 680, 300, 20);
                    changePass.setForeground(Color.red);
                    f.add(changePass);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        displayTotalSalary(employeeId);
        displayTaxDetails(employeeId);


        leaveFrom = new JLabel("Leave From:");
        leaveFrom.setFont(new Font("Arial", Font.BOLD, 15));
        leaveFrom.setBounds(680, 300, 100, 50);

        leaveTo = new JLabel("Leave To:");
        leaveTo.setFont(new Font("Arial", Font.BOLD, 15));
        leaveTo.setBounds(680, 340, 100, 50);

        SpinnerNumberModel dayModelFrom = new SpinnerNumberModel(1, 1, 31, 1);
        SpinnerListModel monthModelFrom = new SpinnerListModel(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        SpinnerNumberModel dayModelTo = new SpinnerNumberModel(1, 1, 31, 1);
        SpinnerListModel monthModelTo = new SpinnerListModel(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        int currentYear = Year.now().getValue();
        SpinnerNumberModel yearModelFrom = new SpinnerNumberModel(currentYear, currentYear, currentYear + 5, 1);
        SpinnerNumberModel yearModelTo = new SpinnerNumberModel(currentYear, currentYear, currentYear + 5, 1);


        employeeDayFrom = new JSpinner(dayModelFrom);
        employeeDayFrom.setBounds(775, 310, 50, 30);
        employeeMonthFrom = new JSpinner(monthModelFrom);
        employeeMonthFrom.setBounds(825, 310, 80, 30);
        employeeYearFrom = new JSpinner(yearModelFrom);
        employeeYearFrom.setBounds(905, 310, 50, 30);


        employeeDayTo = new JSpinner(dayModelTo);
        employeeDayTo.setBounds(775, 345, 50, 30);
        employeeMonthTo = new JSpinner(monthModelTo);
        employeeMonthTo.setBounds(825, 345, 80, 30);
        employeeYearTo = new JSpinner(yearModelTo);
        employeeYearTo.setBounds(905, 345, 50, 30);


        requestLeave = new JTextArea();
        requestLeave.setBounds(680, 410, 300, 200);
        requestLeave.setLineWrap(true);
        requestLeave.setWrapStyleWord(true);
        requestLeave.setBorder(BorderFactory.createCompoundBorder(requestLeave.getBorder(), BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY)));

        submitDataButton = new JButton("submit");
        submitDataButton.setBackground(color);
        submitDataButton.setFont(new Font("Arial", 2, 0));
        submitDataButton.setBounds(780, 630, 85, 38);
        submitDataButton.setIcon(submit);
        submitDataButton.setHorizontalTextPosition(JButton.CENTER);
        submitDataButton.setVerticalTextPosition(JButton.CENTER);
        submitDataButton.addActionListener(this);

        statusLabel = new JLabel("Leave Request:");
        statusLabel.setBounds(680, 280, 300, 20);
        statusLabel.setFont(new Font("Arial", 1, 25));
        statusLabel.setForeground(Color.BLACK);

        statusUpdate = new JLabel();
        statusUpdate.setBounds(870, 280, 130, 20);
        statusUpdate.setFont(new Font("Arial", 2, 20));
        statusUpdate.setForeground(Color.BLACK);

        changePassword = new JButton("change_password");
        changePassword.setBounds(20, 640, 160, 50);
        changePassword.setHorizontalTextPosition(JButton.CENTER);
        changePassword.setVerticalTextPosition(JButton.CENTER);
        changePassword.setBackground(color);
        changePassword.setFont((new Font("Arial", 1, 13)));
        changePassword.setForeground(Color.WHITE);
        changePassword.addActionListener(this);


        String sqlCheck = "select status from leaverequest where emp_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlCheck);
            statement.setInt(1, employeeId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int status = result.getInt("status");
                if (status == 0) {
                    System.out.println("sent in req");
                    statusUpdate.setText("");
                } else if (status == 1) {
                    System.out.println("pending");
                    statusUpdate.setText("Pending");
                    statusUpdate.setForeground(Color.GRAY);
                } else if (status == 2) {
                    System.out.println("approved");
                    statusUpdate.setText("Approved");
                    statusUpdate.setForeground(Color.GREEN);
                } else {
                    System.out.println("rejected");
                    statusUpdate.setText("Rejected");
                    statusUpdate.setForeground(Color.RED);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        f.add(changePassword);
        f.add(requestLeave);
        f.add(submitDataButton);
        f.add(statusLabel);
        f.add(statusUpdate);
        f.add(welcomeLabel);
        f.add(timeLabel);
        f.add(leaveFrom);
        f.add(leaveTo);
        f.add(crossButton);
        f.add(colorPanel);
        f.add(employeeDayTo);
        f.add(employeeMonthTo);
        f.add(employeeYearTo);
        f.add(employeeDayFrom);
        f.add(employeeYearFrom);
        f.add(employeeMonthFrom);

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

    public void updateDateTime(JLabel label) {
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentDate);
        label.setText(formattedDateTime);
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

        if (cmd.equals("submit")) {
            String leaveRequest = requestLeave.getText();
            System.out.println("submit" + idFinal);
            int dayFrom = (int) employeeDayFrom.getValue();
            String monthValueFrom = (String) employeeMonthFrom.getValue();
            int monthFrom = convertMonthToInt(monthValueFrom);
            int yearFrom = (int) employeeYearFrom.getValue();
            String dataFrom = yearFrom + "-" + monthFrom + "-" + dayFrom;
            System.out.println(dataFrom);

            int dayTo = (int) employeeDayTo.getValue();
            String monthValueTo = (String) employeeMonthTo.getValue();
            int monthTo = convertMonthToInt(monthValueTo);
            int yearTo = (int) employeeYearTo.getValue();
            String dataTo = yearTo + "-" + monthTo + "-" + dayTo;
            System.out.println(dataTo);

            System.out.println(leaveRequest);

            String sql = "insert into leaverequest(fromdate,todate,reason,emp_id,status) values(?,?,?,?,?)";
            try {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, dataFrom);
                statement.setString(2, dataFrom);
                statement.setString(3, leaveRequest);
                statement.setInt(4, idFinal);
                statement.setInt(5, 1);
                statement.executeUpdate();
                statusUpdate.getParent().repaint();
                statusUpdate.getParent().repaint();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (cmd.equals("change_password")) {
            System.out.println("change");
            String password = JOptionPane.showInputDialog(null, "Enter new password:", "Change Password", JOptionPane.PLAIN_MESSAGE);
            if (password != null) {
                String sql = "update employee set password=? where id=?";
                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, password);
                    statement.setInt(2, idFinal);
                    statement.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(password);
            } else {
                System.out.println("Input cancelled");
            }
        }

    }

    public int convertMonthToInt(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return -1;
        }
    }

    public void displayTotalSalary(int id) {
        employeeSalaryPanel = new JPanel();
        employeeSalaryPanel.setBackground(Color.lightGray);
        employeeSalaryPanel.setBounds(250, 100, 300, 120);
        try {
            String sql = "SELECT salary AS totalemployeeexpense FROM employee WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                float totalEmployeeExpense = rs.getFloat("totalemployeeexpense");
                JLabel totalExpense = new JLabel("<html>Monthly Salary:<br>" + "<center>" + NumberFormat.getInstance(Locale.US).format(totalEmployeeExpense));
                totalExpense.setFont(new Font("Arial", Font.BOLD, 35));
                System.out.println(totalEmployeeExpense);
                employeeSalaryPanel.add(totalExpense);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(employeeSalaryPanel);
    }

    public void displayTaxDetails(int id) {
        employeePFPanel = new JPanel();
        employeePFPanel.setBackground(Color.lightGray);
        employeePFPanel.setBounds(600, 100, 300, 120);

        employeeSocialPanel = new JPanel();
        employeeSocialPanel.setBackground(Color.lightGray);
        employeeSocialPanel.setBounds(250, 250, 300, 120);

        employeeTaxPanel = new JPanel();
        employeeTaxPanel.setBackground(Color.lightGray);
        employeeTaxPanel.setBounds(250, 400, 300, 120);

        employeeInsurancePanel = new JPanel();
        employeeInsurancePanel.setBackground(Color.lightGray);
        employeeInsurancePanel.setBounds(250, 550, 300, 120);
        try {
            String sql = "SELECT tax,provident_fund,insurance,socialsecurity from tax where empid=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String tax = rs.getString("tax");
                String provident_fund = rs.getString("provident_fund");
                String insurance = rs.getString("insurance");
                String socialsecurity = rs.getString("socialsecurity");
                System.out.println(tax + " " + provident_fund + " " + insurance + " " + socialsecurity);

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                tax = numberFormat.format(Double.parseDouble(tax));
                provident_fund = numberFormat.format(Double.parseDouble(provident_fund));
                insurance = numberFormat.format(Double.parseDouble(insurance));
                socialsecurity = numberFormat.format(Double.parseDouble(socialsecurity));

                JLabel totalTax = new JLabel("<html><center>Yearly Tax:<br>" + "<center>" + tax);
                totalTax.setFont(new Font("Arial", Font.BOLD, 35));
                JLabel totalSocial = new JLabel("<html><center>Social Security<br> Contribution:<br>" + "<center>" + socialsecurity);
                totalSocial.setFont(new Font("Arial", Font.BOLD, 33));
                JLabel totalPF = new JLabel("<html><center>Provident Fund<br> Contribution:<br>" + "<center>" + provident_fund);
                totalPF.setFont(new Font("Arial", Font.BOLD, 33));
                JLabel totalInsurance = new JLabel("<html><center>Insurance Cost:<br>" + "<center>" + insurance);
                totalInsurance.setFont(new Font("Arial", Font.BOLD, 35));

                employeeInsurancePanel.add(totalInsurance);
                employeeSocialPanel.add(totalSocial);
                employeePFPanel.add(totalPF);
                employeeTaxPanel.add(totalTax);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        f.add(employeeInsurancePanel);
        f.add(employeeSocialPanel);
        f.add(employeePFPanel);
        f.add(employeeTaxPanel);
    }
}
