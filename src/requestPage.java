import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

public class requestPage implements ActionListener, MouseListener {
    JFrame f = new JFrame("Home");
    JPanel colorPanel;

    JButton homeLogo;

    JLabel crossButton;
    Icon crossEmpty = new ImageIcon("crossEmpty.png");
    Icon crossFill = new ImageIcon("crossFill.png");
    Color color = new Color(45, 93, 168);
    Connection con = databaseConnection.getConnection();
    Icon homePageLogo = new ImageIcon("homePageLogo.png");
    requestPage(){
        setRequestPage();
        colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setBounds(0, 0, 200, 700);

        f.getContentPane().setBackground(Color.white);
        f.setSize(1000, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public void setRequestPage(){
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
}
