package src.screens;

import src.Config;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import src.components.FadeButton;
import src.db.DBConnection;
import src.models.User;

public class LoginForm extends JFrame 
{
    final private Font mainFont = new Font("poppins", Font.BOLD, 16);
    JTextField usernameField;
    JPasswordField passwordField;


    public void initialize()
    {
        JLabel loginFormLabel = new JLabel("Login Form", SwingConstants.CENTER);
        loginFormLabel.setFont(new Font("poppins", Font.BOLD, 25));
        loginFormLabel.setBackground(new Color(44, 44, 44));

        JLabel usernameLabel = new JLabel("Username: ", SwingConstants.LEFT);
        usernameLabel.setFont(mainFont);

        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.LEFT);
        passwordLabel.setFont(mainFont);
        

        usernameField = new JTextField();
        usernameField.setFont(new Font("poppins", Font.PLAIN, 16));
        usernameField.setOpaque(true);
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(71, 120, 230)));


        passwordField = new JPasswordField();
        passwordField.setFont(new Font("poppins", Font.PLAIN, 16));
        passwordField.setOpaque(true);
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(71, 120, 230)));
        

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 30, 15));


        formPanel.add(loginFormLabel);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JButton btnLogin = new FadeButton("Login", new Color(71, 120, 200), new Color(71, 120, 230), Color.WHITE);

        btnLogin.setPreferredSize(new Dimension(324, 35));

        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = String.valueOf(passwordField.getPassword()).trim();

                User user = getAuthenticatedUser(username, password);
                
                if(user != null) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                    
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Username or password incorrect !", "Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        formPanel.add(btnLogin);

        add(formPanel, BorderLayout.NORTH);

        setTitle("School System - Login Form ");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
        setBackground(new Color(44, 44, 44));
        setIconImage(Config.icon.getImage());
        
    }


    public User getAuthenticatedUser(String username, String password)
    {
        User user = null;

        Connection con = DBConnection.initialize();

        try {
        
            // To avoid SQL Injection attacks
            PreparedStatement statement = con.prepareStatement("SELECT id, full_name, email FROM users WHERE username = ? AND password = ? LIMIT 1");
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();

        
            if (resultSet.getRow() != -1) {

                while (resultSet.next()) 
                {
                    user = new User (
                        resultSet.getInt("id"), 
                        resultSet.getString("full_name"), 
                        username,
                        resultSet.getString("email"), 
                        password
                    );
                }

            } else {
                JOptionPane.showMessageDialog(LoginForm.this, "ERROR", "Try again", JOptionPane.OK_OPTION); 
            }
                   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(LoginForm.this, "ERROR", e.getMessage(), JOptionPane.OK_OPTION); 
        }

        

        return user;
    }
}