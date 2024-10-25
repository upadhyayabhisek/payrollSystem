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

public class loginPage implements ActionListener, MouseListener {
    JFrame f = new JFrame("Login Page");
    JButton loginButton, employeeLoginButton;
    JTextField emailTextField;
    JPanel colorPanel, textPanel;
    JLabel projTitle, loginTitle, usernameLabel, passwordLabel, showPasswordLabel,employeeImageLabel;

    JCheckBox showPasswordBox;

    JPasswordField passwordField;
    JLabel crossButton;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Icon employeeIcon = new ImageIcon("employeeIcon.png");

    loginPage() {
        addLoginPageComponents();
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setResizable(false);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public void addLoginPageComponents() {
        Color colorBackground = new Color(45, 93, 168);
        Color colorText = new Color(255, 255, 255);
        Icon i = new ImageIcon("login.png");
        Icon empIcon = new ImageIcon("employeeIcon.png");

        colorPanel = new JPanel();
        colorPanel.setBackground(colorBackground);
        colorPanel.setBounds(0, 0, 400, 700);

        textPanel = new JPanel();
        textPanel.setBackground(colorText);
        textPanel.setBounds(400, 0, 600, 700);

        emailTextField = new JTextField();
        emailTextField.setBounds(200, 230, 230, 40);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 310, 230, 40);

        projTitle = new JLabel("Payroll System");
        projTitle.setFont(new Font("Verdana", Font.BOLD, 40));
        projTitle.setBounds(40, 300, 400, 100);

        employeeImageLabel = new JLabel();
        employeeImageLabel.setBounds(60, 130, 400, 200);
        employeeImageLabel.setIcon(empIcon);

        loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Arial", 0, 40));
        loginTitle.setBounds(50, 50, 250, 50);

        usernameLabel = new JLabel("Enter Email:");
        usernameLabel.setFont(new Font("Arial", 0, 20));
        usernameLabel.setBounds(70, 225, 250, 50);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", 0, 20));
        passwordLabel.setBounds(70, 305, 250, 50);

        showPasswordLabel = new JLabel("Show Password:");
        showPasswordLabel.setFont(new Font("Arial", 1, 15));
        showPasswordLabel.setBounds(75, 430, 250, 50);


        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", 1, 0));
        loginButton.setBounds(200, 380, 110, 50);
        loginButton.setIcon(i);
        loginButton.addActionListener(this);
        loginButton.setHorizontalTextPosition(JButton.CENTER);
        loginButton.setVerticalTextPosition(JButton.CENTER);

        employeeLoginButton = new JButton("Employee");
        employeeLoginButton.setFont(new Font("Arial", 1, 0));
        employeeLoginButton.setBounds(320, 380, 110, 50);
        employeeLoginButton.addActionListener(this);
        employeeLoginButton.setIcon(empIcon);
        employeeLoginButton.setHorizontalTextPosition(JButton.CENTER);
        employeeLoginButton.setVerticalTextPosition(JButton.CENTER);

        showPasswordBox = new JCheckBox();
        showPasswordBox.setBounds(193, 444, 25, 25);
        showPasswordBox.setOpaque(false);
        showPasswordBox.addActionListener(this);

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);

        textPanel.setLayout(null);
        textPanel.add(loginTitle);
        textPanel.add(loginButton);
        textPanel.add(usernameLabel);
        textPanel.add(passwordLabel);
        textPanel.add(showPasswordLabel);
        textPanel.add(emailTextField);
        textPanel.add(passwordField);
        textPanel.add(showPasswordBox);
        //textPanel.add(employeeLoginButton);
        f.add(crossButton);
        f.add(projTitle);
        f.add(employeeImageLabel);
        f.add(colorPanel);
        f.add(textPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        String email = emailTextField.getText();
        String password = new String(passwordField.getPassword());

        if (showPasswordBox.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('*');
        }

        if (cmd.equals("Employee")) {
            System.out.println("employee");
            employeePortalLogin();
        }

        if (cmd.equals("Login")) {
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Please enter email and password!", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Connection con = databaseConnection.getConnection();
                    String sql = "SELECT * FROM loginform WHERE email=? AND passvalue=?";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, password);
                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                        System.out.println("Login successful: " + email);
                        new homePage();
                        f.setVisible(false);
                        f.getContentPane().removeAll();
                        f.dispose();
                    } else {
                        JOptionPane.showMessageDialog(f, "Incorrect email or password!", "Error", JOptionPane.WARNING_MESSAGE);
                        System.out.println("Incorrect email or password");
                    }
                    statement.close();
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("SQL Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
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

    public void employeePortalLogin() {
        String email = emailTextField.getText();
        String password = new String(passwordField.getPassword());
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please enter email and password!", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Connection con = databaseConnection.getConnection();
                String sql = "SELECT * FROM employee WHERE email=? AND password=?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    System.out.println("Login successful: " + email);
                    String name = result.getString("name");
                    String id = result.getString("id");
                    new employeePortalPage(name, id);
                    f.setVisible(false);
                    f.getContentPane().removeAll();
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(f, "Incorrect email or password!", "Error", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Incorrect email or password");
                }
                statement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}