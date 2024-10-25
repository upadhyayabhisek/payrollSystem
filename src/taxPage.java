import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class taxPage extends JFrame implements ActionListener, MouseListener {

    static double tax = 0;
    JTextField nameText, incomeText, socialSecurityText, insuranceText,allowanceText,bonusText, employeeProvidentText, searchBar;
    JLabel displayTaxLabel, nameLabel, incomeLabel,allowanceLabel,bonusLabel, socialSecurityLabel, insuranceLabel, employeeProvidentLabel1, employeeProvidentLabel2;
    JButton calculateTaxButton, saveButton, searchButton, clearButton,downloadButton;
    Connection con = databaseConnection.getConnection();

    JButton homeLogo;
    JLabel crossButton;
    JPanel colorPanel;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Icon clear = new ImageIcon("cleardata.png");
    Icon save = new ImageIcon("savedata.png");
    Icon search = new ImageIcon("search.png");
    Icon calculate = new ImageIcon("calculatedata.png");
    Icon download=new ImageIcon("button_download.png");

    taxPage() throws NullPointerException {
        super("tax");
        setTaxPageComponents();

        getContentPane().setBackground(Color.white);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }

    public void setTaxPageComponents(){

        Color color = new Color(45, 93, 168);
        Icon homePageLogo = new ImageIcon("homePageLogo.png");

        JLabel displayLabel = new JLabel("Tax Calculation:");
        displayLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        displayLabel.setBounds(220, 40, 400, 50);
        add(displayLabel);

        JLabel searchLabel = new JLabel("Search ID:");
        searchLabel.setFont(new Font("Arial", 3, 18));
        searchLabel.setBounds(220,100, 100, 45);

        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        searchBar = new JTextField();
        searchBar.setBounds(320, 100, 500, 43);

        searchButton = new JButton("search");
        searchButton.setBackground(color);
        searchButton.setBounds(830, 100, 42, 42);
        searchButton.setIcon(search);
        searchButton.setHorizontalTextPosition(JButton.CENTER);
        searchButton.setVerticalTextPosition(JButton.CENTER);
        searchButton.addActionListener(this);

        homeLogo = new JButton("home");
        homeLogo.setFont(new Font("Arial", 2, 0));
        homeLogo.setBounds(0, 0, 46, 46);
        homeLogo.setIcon(homePageLogo);
        homeLogo.setBackground(color);
        homeLogo.setHorizontalTextPosition(JButton.CENTER);
        homeLogo.setVerticalTextPosition(JButton.CENTER);
        homeLogo.addActionListener(this);
        homeLogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("home");
                new homePage();
                setVisible(false);
                dispose();
            }
        });

        crossButton = new JLabel();
        crossButton.setIcon(crossEmpty);
        crossButton.setBounds(950, 0, 50, 50);
        crossButton.addMouseListener(this);
        crossButton.setHorizontalTextPosition(JLabel.CENTER);
        crossButton.setVerticalTextPosition(JLabel.CENTER);


        incomeText = new JTextField();
        socialSecurityText = new JTextField();
        insuranceText = new JTextField();
        employeeProvidentText = new JTextField();
        nameText = new JTextField();
        allowanceText=new JTextField();
        bonusText=new JTextField();

        nameLabel = new JLabel("Name:");
        incomeLabel = new JLabel("Yearly Salary:");
        socialSecurityLabel = new JLabel("Social Security fund:");
        insuranceLabel = new JLabel("Insurance:");
        employeeProvidentLabel1 = new JLabel("Employees Provident:");
        employeeProvidentLabel2 = new JLabel("Fund:");
        allowanceLabel=new JLabel("Allowance");
        bonusLabel=new JLabel("Bonus");
        displayTaxLabel = new JLabel();
        displayTaxLabel.setFont(new Font("Arial", Font.BOLD, 20));
        incomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        socialSecurityLabel.setFont(new Font("Arial", Font.BOLD, 15));
        insuranceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        employeeProvidentLabel1.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        employeeProvidentLabel2.setFont(new Font("Arial", Font.BOLD, 15));
        allowanceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bonusLabel.setFont(new Font("Arial", Font.BOLD, 15));

        calculateTaxButton = new JButton("calculate");
        calculateTaxButton.setFont(new Font("Arial", Font.BOLD, 0));
        calculateTaxButton.setIcon(calculate);
        calculateTaxButton.setBounds(250, 620, 120, 40);
        calculateTaxButton.addActionListener(this);
        calculateTaxButton.setHorizontalTextPosition(JButton.CENTER);
        calculateTaxButton.setVerticalTextPosition(JButton.CENTER);

        saveButton = new JButton("save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 0));
        saveButton.setBounds(390, 620, 120, 40);
        saveButton.setIcon(save);
        saveButton.addActionListener(this);
        saveButton.setHorizontalTextPosition(JButton.CENTER);
        saveButton.setVerticalTextPosition(JButton.CENTER);

        clearButton = new JButton("clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 0));
        clearButton.setBounds(530, 620, 120, 40);
        clearButton.addActionListener(this);
        clearButton.setIcon(clear);
        clearButton.setHorizontalTextPosition(JButton.CENTER);
        clearButton.setVerticalTextPosition(JButton.CENTER);

        downloadButton = new JButton("download");
        downloadButton.setFont(new Font("Arial", Font.BOLD, 0));
        downloadButton.setBounds(670, 620, 120, 40);
        downloadButton.addActionListener(this);
        downloadButton.setBackground(color);
        downloadButton.setIcon(download);
        downloadButton.setHorizontalTextPosition(JButton.CENTER);
        downloadButton.setVerticalTextPosition(JButton.CENTER);

        nameLabel.setBounds(250, 170, 200, 50);
        incomeLabel.setBounds(250, 230, 200, 50);
        socialSecurityLabel.setBounds(250, 290, 200, 50);
        insuranceLabel.setBounds(250, 350, 200, 50);
        employeeProvidentLabel1.setBounds(250, 410, 200, 50);
        employeeProvidentLabel2.setBounds(250, 425, 200, 50);
        allowanceLabel.setBounds(250,485,200,50);
        bonusLabel.setBounds(250,545,200,50);

        displayTaxLabel.setBounds(760, 260, 200, 250);

        nameText.setBounds(500, 170, 200, 40);
        incomeText.setBounds(500, 230, 200, 40);
        socialSecurityText.setBounds(500, 290, 200, 40);
        insuranceText.setBounds(500, 350, 200, 40);
        employeeProvidentText.setBounds(500, 410, 200, 40);
        allowanceText.setBounds(500,485,200,40);
        bonusText.setBounds(500,545,200,40);

        nameText.setEditable(false);
        incomeText.setEditable(false);
        nameText.setBackground(Color.white);
        incomeText.setBackground(Color.white);

        add(homeLogo);
        add(crossButton);
        add(colorPanel);
        add(searchLabel);
        add(searchBar);
        add(searchButton);
        add(clearButton);
        add(nameLabel);
        add(incomeLabel);
        add(socialSecurityLabel);
        add(insuranceLabel);
        add(employeeProvidentLabel1);
        add(employeeProvidentLabel2);
        add(allowanceLabel);
        add(bonusLabel);
        add(nameText);
        add(incomeText);
        add(socialSecurityText);
        add(insuranceText);
        add(employeeProvidentText);
        add(allowanceText);
        add(bonusText);
        add(calculateTaxButton);
        add(saveButton);
        add(downloadButton);
        add(displayTaxLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        int taxableIncome, income, insuranceDeduction, socialSecurity, employeeProvident,bonus=0,allowance=0;

        if (cmd.equals("clear")) {
            clearRecordTextField();
        }

        if (cmd.equals("search")) {
            searchDataFromDatabase();
        }

        if (cmd.equals("calculate")) {
            String name = nameText.getText();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "No record searched!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                income = Integer.parseInt(incomeText.getText());
                try {
                    insuranceDeduction = Integer.parseInt(insuranceText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid insurance deduction", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    socialSecurity = Integer.parseInt(socialSecurityText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid social security", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    employeeProvident = Integer.parseInt(employeeProvidentText.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid employee provident", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                if (socialSecurity >= 500000 || socialSecurity > (0.31 * income)) {
                    System.out.println("Social security should be 31% where 11% from employee and 20% from employer and not over 500k");

                    JOptionPane.showMessageDialog(null, "Social security should be 31% where 11% from employee and 20% from employer and not over 500k!\nEnter Again!", "Error", JOptionPane.ERROR_MESSAGE);
                    socialSecurity = Integer.parseInt(socialSecurityText.getText());
                } else if (employeeProvident > (0.1 * (income / 12))) {

                    System.out.println("Employee Provided cant be more than 10% of monthly income");
                    JOptionPane.showMessageDialog(null, "Employee Provided cant be more than 10% of monthly income!\nEnter Again!", "Error", JOptionPane.ERROR_MESSAGE);
                    employeeProvident = Integer.parseInt(employeeProvidentText.getText());
                } else if (insuranceDeduction > 40000) {

                    JOptionPane.showMessageDialog(null, "Insurance cant be over 40k annually!\nEnter Again!", "Error", JOptionPane.ERROR_MESSAGE);
                    insuranceDeduction = Integer.parseInt(insuranceText.getText());
                } else {
                    try {
                         allowance = 0;
                         bonus = 0;
                        if (!allowanceText.getText().isEmpty()) {
                            allowance = Integer.parseInt(allowanceText.getText());
                        }
                        if (!bonusText.getText().isEmpty()) {
                            bonus = Integer.parseInt(bonusText.getText());
                        }
                        System.out.println(allowance + bonus);
                    } catch (NumberFormatException ex) {
                        System.out.println("allowance 0 bonus 0.");
                    }

                    taxableIncome = (((income+allowance+bonus) - insuranceDeduction) - socialSecurity) - employeeProvident;

                    if (taxableIncome <= 500000) {
                        tax = (taxableIncome) * 0.01;
                    } else if (taxableIncome <= 700000) {
                        tax = 5000 + (taxableIncome - 500000) * 0.1;
                    } else if (taxableIncome <= 1000000) {
                        tax = 25000 + (taxableIncome - 700000) * 0.2;
                    } else if (taxableIncome <= 2000000) {
                        tax = 85000 + (taxableIncome - 1000000) * 0.3;
                    } else {
                        tax = 385000 + (taxableIncome - 2000000) * 0.36;
                    }

                    System.out.print("Income tax: " + ((int) (tax * 100)) / 100.00);
                    System.out.println("\nSocial Security:" + socialSecurity);
                    System.out.println("\nPF:" + employeeProvident);
                    System.out.println("\nInsurance:" + insuranceDeduction);


                    displayTaxLabel.setText("<html> Calculated Records:" + "<br>Name: " + name + "<br>Income: " + income+ "<br>Allowance: " + allowance+ "<br>Bonus: " + bonus + "<br>SSF: " + socialSecurity + "<br>EP: " + employeeProvident + "<br>Insurance: " + insuranceDeduction + "<br>Tax: " + ((tax * 100) / 100.000) + "</html>");
                }

            }
        }

        if (cmd.equals("save")) {
            System.out.println("saved" + tax);
            if (tax == 0) {
                JOptionPane.showMessageDialog(null, "Tax not calculated\nTry Again!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("database");
                try {
                    String sql = "INSERT INTO tax(empid, tax, provident_fund, insurance, socialsecurity,bonus,allowance) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, searchBar.getText());
                    statement.setString(2, String.valueOf(tax));
                    statement.setInt(3, Integer.parseInt(employeeProvidentText.getText()));
                    statement.setInt(4, Integer.parseInt(insuranceText.getText()));
                    statement.setInt(5, Integer.parseInt(socialSecurityText.getText()));
                    statement.setInt(6, Integer.parseInt(bonusText.getText()));
                    statement.setInt(7, Integer.parseInt(allowanceText.getText()));
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("added");
                        JOptionPane.showMessageDialog(null, "Record Added!", "Success", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
        if(cmd.equals("download")){
            String fileName="emp"+searchBar.getText()+".txt";
            System.out.println("download");
            if (nameText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name before downloading.");
                return;
            }
            String nameFile = nameText.getText();
            double incomeFile = Double.parseDouble(incomeText.getText());
            double socialSecurityFile = Double.parseDouble(socialSecurityText.getText());
            double insuranceFile = Double.parseDouble(insuranceText.getText());
            double allowanceFile = Double.parseDouble(allowanceText.getText());
            double bonusFile = Double.parseDouble(bonusText.getText());
            double employeeProvidentFile = Double.parseDouble(employeeProvidentText.getText());
            double taxFile=tax;
            String paySlipContent = generatePaySlip(nameFile, incomeFile, socialSecurityFile, insuranceFile, allowanceFile, bonusFile, employeeProvidentFile, taxFile);
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write(paySlipContent);
                writer.close();
                JOptionPane.showMessageDialog(null, "Pay slip downloaded as "+fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while downloading the pay slip.");
            }

        }
    }

    private String generatePaySlip(String name, double income, double socialSecurity, double insurance, double allowance, double bonus, double employeeProvident, double tax) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedIncome = decimalFormat.format(income);
        String formattedSocialSecurity = decimalFormat.format(socialSecurity);
        String formattedInsurance = decimalFormat.format(insurance);
        String formattedAllowance = decimalFormat.format(allowance);
        String formattedBonus = decimalFormat.format(bonus);
        String formattedEmployeeProvident = decimalFormat.format(employeeProvident);
        String formattedTax = decimalFormat.format(tax);

        StringBuilder paySlip = new StringBuilder();
        paySlip.append("-------------------------------------------------\n");
        paySlip.append("                  PAYSLIP\n");
        paySlip.append("-------------------------------------------------\n");
        paySlip.append("Name: ").append(name).append("\n");
        paySlip.append("-------------------------------------------------\n");
        paySlip.append("Income:              NPR.").append(formattedIncome).append("\n");
        paySlip.append("Social Security:     NPR.").append(formattedSocialSecurity).append("\n");
        paySlip.append("Insurance:           NPR.").append(formattedInsurance).append("\n");
        paySlip.append("Allowance:           NPR.").append(formattedAllowance).append("\n");
        paySlip.append("Bonus:               NPR.").append(formattedBonus).append("\n");
        paySlip.append("Employee Provident:  NPR.").append(formattedEmployeeProvident).append("\n");
        paySlip.append("-------------------------------------------------\n");
        paySlip.append("Tax:                 NPR.").append(formattedTax).append("\n");
        paySlip.append("-------------------------------------------------\n");

        return paySlip.toString();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        dispose();
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

    public void searchDataFromDatabase() {
        String id = searchBar.getText();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter ID to search", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("search" + id);
            System.out.println(id);
            int employeeId = Integer.parseInt(id);
            try {
                PreparedStatement statement = con.prepareStatement("SELECT name, salary FROM employee WHERE id = ?");
                statement.setInt(1, employeeId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String salary = resultSet.getString("salary");
                    int blankSalary = 12 * Integer.parseInt(salary);
                    salary = Integer.toString(blankSalary);
                    System.out.println("Name: " + name);
                    System.out.println("Salary: " + salary);
                    nameText.setText(name);
                    incomeText.setText(salary);
                } else {
                    System.out.println("No employee found with ID: " + employeeId);
                    JOptionPane.showMessageDialog(null, "No corresponding id found!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void clearRecordTextField() {
        searchBar.setText("");
        nameText.setText("");
        insuranceText.setText("");
        employeeProvidentText.setText("");
        socialSecurityText.setText("");
        displayTaxLabel.setText("");
        incomeText.setText("");
    }
}
