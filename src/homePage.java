import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class homePage implements ActionListener, MouseListener {
    JFrame f = new JFrame("Home");
    JLabel welcomeLabel;
    JPanel colorPanel;
    JButton employeeButton, logOutButton, exitAll, reportButton, addUser, taxButton, analysisButton,attendanceButton;
    JPanel testPanel;

    JLabel crossButton;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Color color = new Color(45, 93, 168);
    //employee add,remove,search,delete record
    //salary ma calculate payroll and save
    //attendence button
    //report button to show all employee time worked earning tax and dedcution

    homePage() {


        addHomePageComponents();


        f.getContentPane().setBackground(Color.white);
        f.setSize(1000, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public void addHomePageComponents(){
        Font font = new Font("Arial", Font.BOLD, 0);
        Font font2 = new Font("Arial", Font.BOLD, 15);

        Icon icon = new ImageIcon("logout.png");
        Icon employeeIcon = new ImageIcon("employee.png");
        Icon homepageIcon=new ImageIcon("pngegg.png");
        Icon taxIcon = new ImageIcon("tax.png");
        Icon exitIcon = new ImageIcon("exit.png");
        Icon reportIcon = new ImageIcon("report.png");
        Icon analysisIcon = new ImageIcon("analysis.png");
        Icon attendanceIcon=new ImageIcon("button_attendance.png");

        welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", 2, 35));
        welcomeLabel.setBounds(250, 30, 300, 50);

        JLabel homeLabel=new JLabel();
        homeLabel.setBounds(250,50,800,700);
        homeLabel.setIcon(homepageIcon);
        f.add(homeLabel);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        employeeButton = new JButton("Employee");
        employeeButton.setBounds(20, 60, 149, 60);
        employeeButton.setIcon(employeeIcon);
        employeeButton.setHorizontalTextPosition(JButton.CENTER);
        employeeButton.setVerticalTextPosition(JButton.CENTER);
        employeeButton.setBackground(color);

        reportButton = new JButton("Report");
        reportButton.setBounds(20, 260, 149, 60);
        reportButton.setIcon(reportIcon);
        reportButton.setHorizontalTextPosition(JButton.CENTER);
        reportButton.setVerticalTextPosition(JButton.CENTER);
        reportButton.setBackground(color);

        taxButton = new JButton("Tax");
        taxButton.setBounds(20, 160, 149, 60);
        taxButton.setIcon(taxIcon);
        taxButton.setHorizontalTextPosition(JButton.CENTER);
        taxButton.setVerticalTextPosition(JButton.CENTER);
        taxButton.setBackground(color);

        analysisButton = new JButton("analysis");
        analysisButton.setBounds(20, 360, 149, 60);
        analysisButton.setIcon(analysisIcon);
        analysisButton.setHorizontalTextPosition(JButton.CENTER);
        analysisButton.setVerticalTextPosition(JButton.CENTER);
        analysisButton.setBackground(color);
        analysisButton.setFont(new Font("Arial", 2, 0));


        attendanceButton = new JButton("attendance");
        attendanceButton.setBounds(20, 460, 149, 60);
        attendanceButton.setIcon(attendanceIcon);
        attendanceButton.setHorizontalTextPosition(JButton.CENTER);
        attendanceButton.setVerticalTextPosition(JButton.CENTER);
        attendanceButton.setBackground(color);
        attendanceButton.setFont(new Font("Arial", 2, 0));

        logOutButton = new JButton("Logout");
        logOutButton.setBounds(130, 640, 40, 40);
        logOutButton.setIcon(icon);
        logOutButton.setHorizontalTextPosition(JButton.CENTER);
        logOutButton.setVerticalTextPosition(JButton.CENTER);
        logOutButton.setBackground(color);
        logOutButton.setFont(new Font("Arial", 2, 0));

        exitAll = new JButton("Exit");
        exitAll.setBounds(20, 560, 149, 60);
        exitAll.setIcon(exitIcon);
        exitAll.setHorizontalTextPosition(JButton.CENTER);
        exitAll.setVerticalTextPosition(JButton.CENTER);
        exitAll.setBackground(color);

        addUser = new JButton("Add User");
        addUser.setBounds(20, 640, 100, 40);
        addUser.setHorizontalTextPosition(JButton.CENTER);
        addUser.setVerticalTextPosition(JButton.CENTER);
        addUser.setBackground(color);

        employeeButton.addActionListener(this);
        reportButton.addActionListener(this);
        addUser.addActionListener(this);
        logOutButton.addActionListener(this);
        exitAll.addActionListener(this);
        taxButton.addActionListener(this);
        analysisButton.addActionListener(this);
        attendanceButton.addActionListener(this);


        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);

        employeeButton.setFont(font);
        exitAll.setFont(font);
        reportButton.setFont(font);
        addUser.setFont(font2);
        taxButton.setFont(font);

        testPanel = new JPanel();
        testPanel.setBackground(new Color(0, 0, 0, 0));
        testPanel.setBounds(250, 150, 270, 110);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        mainPanel.setOpaque(true);
        mainPanel.setBounds(560, 390, 450, 320);

        CalendarPanel calendarPanel = new CalendarPanel();
        calendarPanel.setBounds(0, 0, 430, 300);
        calendarPanel.setBackground(new Color(0, 0, 0, 0));
        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        f.getContentPane().add(mainPanel);

        colorPanel.setLayout(null);

        colorPanel.add(employeeButton);
        colorPanel.add(exitAll);
        colorPanel.add(reportButton);
        colorPanel.add(taxButton);
        colorPanel.add(addUser);
        colorPanel.add(logOutButton);
        colorPanel.add(analysisButton);
        colorPanel.add(attendanceButton);

        f.add(testPanel);
        f.add(crossButton);
        f.add(colorPanel);
        f.add(welcomeLabel);

        JLayeredPane layeredPane = f.getLayeredPane();
        layeredPane.add(homeLabel, Integer.valueOf(0));
        layeredPane.add(mainPanel, Integer.valueOf(1));
        layeredPane.add(testPanel, Integer.valueOf(1));


        countTotalEmployee();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Logout")) {
            new loginPage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("Employee")) {
            System.out.println("employee");
            new employeePage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("attendance")) {
            System.out.println("attendance");
            new attendancePage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("Add User")) {
            System.out.println("add user");
            addNewUserLoginInfo();
        }

        if (cmd.equals("Exit")) {
            System.out.println("exited");
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("Tax")) {
            System.out.println("tax");
            new taxPage();
            f.setVisible(false);
            f.dispose();
        }

        if (cmd.equals("analysis")) {
            System.out.println("anaysis");
            new analysisPage();
            f.setVisible(false);
            f.dispose();
        }

        if(cmd.equals("Report")){
            System.out.println("report");
            new reportPage();
            f.setVisible(false);
            f.dispose();
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

    public void countTotalEmployee() {
        try {
            Connection con = databaseConnection.getConnection();
            Statement statement = con.createStatement();
            String sql = "select count(*) as totalemployee from employee";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                int totalEmployeeCount = rs.getInt("totalemployee");
                JLabel totalCount = new JLabel("<html>Active Employee:<br>" + "<center>" + totalEmployeeCount);
                totalCount.setFont(new Font("Arial", 1, 30));
                System.out.println(totalEmployeeCount);
                testPanel.add(totalCount);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addNewUserLoginInfo() {
        JTextField xField = new JTextField(10);
        JTextField yField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Email:"));
        myPanel.add(xField);
        //myPanel.add(Box.createVerticalStrut(20));
        myPanel.add(new JLabel("Password:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter email and password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String email = xField.getText();
            String pass = yField.getText();
            try {
                Connection con = databaseConnection.getConnection();
                String sql = "insert into loginform(email,passvalue) values(?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, pass);
                statement.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }
}
