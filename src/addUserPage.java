import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Year;

public class addUserPage implements MouseListener, ActionListener {
    JFrame f = new JFrame();

    JLabel crossButton;
    JPanel colorPanel;
    JButton homeLogo, backButton, clearButton;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Icon submit = new ImageIcon("submitdata.png");
    Icon clear = new ImageIcon("cleardata.png");
    Icon addEmployeeIcon=new ImageIcon("add-employee-icon.png");
    Icon backButtonLogo = new ImageIcon("back.png");
    Color color = new Color(45, 93, 168);

    JTextField employeeName, employeeEmail, employeePhone, employeeAddress, employeeSalary, employeeJobTitle, employeeEducationLevel;
    JLabel employeeNameLabel, employeeEmailLabel, employeePhoneLabel, employeeAddressLabel, employeeSalaryLabel, employeeJobTitleLabel, employeeEducationLevelLabel, employeeGenderLabel, employeeAgeLabel,addEmployeeImageLabel;
    JRadioButton employeeGenderMale, employeeGenderFemale;
    JSpinner employeeYearDOB, employeeMonthDOB, employeeDayDOB;
    JButton submitDataButton;

    addUserPage() {
        addUserPageComponents();

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

    public void addUserPageComponents() {

        Icon homePageLogo = new ImageIcon("homePageLogo.png");
        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);

        JLabel displayLabel = new JLabel("Add Employee:");
        displayLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        displayLabel.setBounds(250, 30, 400, 50);

        f.add(displayLabel);

        backButton = new JButton("back");
        backButton.setFont(new Font("Arial", 2, 0));
        backButton.setBounds(46, 0, 46, 46);
        backButton.setIcon(backButtonLogo);
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        backButton.setBackground(color);
        backButton.addActionListener(this);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);

        employeeName = new JTextField("name");
        employeeName.setBounds(350, 100, 200, 40);
        employeeNameLabel = new JLabel("Full Name:");
        employeeNameLabel.setFont(new Font("Arial", 3, 15));
        employeeNameLabel.setBounds(250, 100, 100, 40);

        employeeEmail = new JTextField("email");
        employeeEmail.setBounds(350, 160, 200, 40);
        employeeEmailLabel = new JLabel("Email:");
        employeeEmailLabel.setFont(new Font("Arial", 3, 15));
        employeeEmailLabel.setBounds(250, 160, 100, 40);

        employeePhone = new JTextField("phone");
        employeePhone.setBounds(350, 220, 200, 40);
        employeePhoneLabel = new JLabel("phone:");
        employeePhoneLabel.setFont(new Font("Arial", 3, 15));
        employeePhoneLabel.setBounds(250, 220, 100, 40);

        employeeAddress = new JTextField("address");
        employeeAddress.setBounds(350, 280, 200, 40);
        employeeAddressLabel = new JLabel("address:");
        employeeAddressLabel.setFont(new Font("Arial", 3, 15));
        employeeAddressLabel.setBounds(250, 280, 100, 40);

        employeeSalary = new JTextField("salary");
        employeeSalary.setBounds(350, 340, 200, 40);
        employeeSalaryLabel = new JLabel("salary:");
        employeeSalaryLabel.setFont(new Font("Arial", 3, 15));
        employeeSalaryLabel.setBounds(250, 340, 100, 40);

        employeeJobTitle = new JTextField("job");
        employeeJobTitle.setBounds(350, 400, 200, 40);
        employeeJobTitleLabel = new JLabel("job title:");
        employeeJobTitleLabel.setFont(new Font("Arial", 3, 15));
        employeeJobTitleLabel.setBounds(250, 400, 100, 40);

        employeeEducationLevel = new JTextField("education");
        employeeEducationLevel.setBounds(350, 460, 200, 40);
        employeeEducationLevelLabel = new JLabel("Education:");
        employeeEducationLevelLabel.setFont(new Font("Arial", 3, 15));
        employeeEducationLevelLabel.setBounds(250, 460, 100, 40);

        employeeGenderLabel = new JLabel("Gender:");
        employeeGenderLabel.setFont(new Font("Arial", 3, 15));
        employeeGenderLabel.setBounds(600, 99, 200, 40);

        employeeAgeLabel = new JLabel("DOB:");
        employeeAgeLabel.setFont(new Font("Arial", 3, 15));
        employeeAgeLabel.setBounds(600, 160, 200, 40);

        submitDataButton = new JButton("submit");
        submitDataButton.setBackground(color);
        submitDataButton.setFont(new Font("Arial", 2, 0));
        submitDataButton.setBounds(350, 550, 85, 38);
        submitDataButton.setIcon(submit);
        submitDataButton.setHorizontalTextPosition(JButton.CENTER);
        submitDataButton.setVerticalTextPosition(JButton.CENTER);
        submitDataButton.addActionListener(this);


        clearButton = new JButton("clear");
        clearButton.setBackground(color);
        clearButton.setFont(new Font("Arial", 2, 0));
        clearButton.setBounds(450, 550, 85, 38);
        clearButton.setIcon(clear);
        clearButton.setHorizontalTextPosition(JButton.CENTER);
        clearButton.setVerticalTextPosition(JButton.CENTER);
        clearButton.addActionListener(this);


        SpinnerNumberModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
        employeeDayDOB = new JSpinner(dayModel);
        employeeDayDOB.setBounds(710, 160, 50, 30);
        SpinnerListModel monthModel = new SpinnerListModel(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        employeeMonthDOB = new JSpinner(monthModel);
        employeeMonthDOB.setBounds(770, 160, 80, 30);
        int currentYear = Year.now().getValue();
        SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, currentYear - 100, currentYear, 1);
        employeeYearDOB = new JSpinner(yearModel);
        employeeYearDOB.setBounds(860, 160, 50, 30);

        employeeGenderMale = new JRadioButton(" Male");
        employeeGenderFemale = new JRadioButton(" Female");
        employeeGenderMale.setBounds(711, 100, 70, 30);
        employeeGenderFemale.setBounds(791, 100, 80, 30);
        employeeGenderMale.setBackground(Color.white);
        employeeGenderFemale.setBackground(Color.white);
        ButtonGroup bg = new ButtonGroup();
        bg.add(employeeGenderFemale);
        bg.add(employeeGenderMale);

        addEmployeeImageLabel=new JLabel();
        addEmployeeImageLabel.setBounds(650,300,300,310);
        addEmployeeImageLabel.setIcon(addEmployeeIcon);
        f.add(addEmployeeImageLabel);

        f.add(employeeAgeLabel);
        f.add(employeeGenderLabel);
        f.add(employeeGenderMale);
        f.add(employeeGenderFemale);
        f.add(submitDataButton);
        f.add(employeeDayDOB);
        f.add(employeeMonthDOB);
        f.add(employeeYearDOB);
        f.add(employeeName);
        f.add(employeeNameLabel);
        f.add(employeeEmail);
        f.add(employeeEmailLabel);
        f.add(employeeAddress);
        f.add(employeeAddressLabel);
        f.add(employeePhone);
        f.add(employeePhoneLabel);
        f.add(employeeSalary);
        f.add(employeeSalaryLabel);
        f.add(employeeJobTitle);
        f.add(employeeEducationLevelLabel);
        f.add(employeeJobTitleLabel);
        f.add(employeeEducationLevel);

        f.add(clearButton);

        f.add(homeLogo);
        f.add(backButton);
        f.add(colorPanel);
        f.add(crossButton);
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
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gender = null;
        String cmd = e.getActionCommand();

        if (cmd.equals("home")) {
            System.out.println("home");
            new homePage();
            f.setVisible(false);
            f.dispose();
        }
        if (cmd.equals("back")) {
            System.out.println("back");
            new employeePage();
            f.setVisible(false);
            f.dispose();
        }

        if (employeeGenderMale.isSelected()) {
            gender = "male";
        } else if (employeeGenderFemale.isSelected()) {
            gender = "female";
        }

        if (cmd.equals("submit")) {
            String testName = employeeName.getText();
            if (testName.isEmpty() || testName.equals("name")) {
                JOptionPane.showMessageDialog(null, "Data not entered!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("submit");
                int day = (int) employeeDayDOB.getValue();
                String monthValue = (String) employeeMonthDOB.getValue();
                int month = convertMonthToInt(monthValue);
                int year = (int) employeeYearDOB.getValue();
                String data = year + "-" + month + "-" + day;
                System.out.println(data);
                System.out.println(gender);
                addDataIntoDB(data, gender);
            }
        }

        if (cmd.equals("clear")) {
            clearRecordTextField();
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


    public void addDataIntoDB(String data, String gender) {
        String name = employeeName.getText();
        String email = employeeEmail.getText();
        String phone = employeePhone.getText();
        String address = employeeAddress.getText();
        String salary = employeeSalary.getText();
        String job = employeeJobTitle.getText();
        String education = employeeEducationLevel.getText();

        System.out.println(data);
        try {
            String sql = "insert into employee(name,email,phone,address,salary,dob,gender,jobtitle,education) values(?,?,?,?,?,?,?,?,?)";
            Connection con = databaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, salary);
            statement.setString(6, data);
            statement.setString(7, gender);
            statement.setString(8, job);
            statement.setString(9, education);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record Added!", "Success", JOptionPane.ERROR_MESSAGE);
            clearRecordTextField();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void clearRecordTextField() {
        employeeName.setText("");
        employeeEmail.setText("");
        employeePhone.setText("");
        employeeAddress.setText("");
        employeeSalary.setText("");
        employeeJobTitle.setText("");
        employeeEducationLevel.setText("");
    }
}
