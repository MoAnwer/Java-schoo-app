package src.screens;

import src.Config;
import javax.swing.*;
import src.components.FadeButton;
import src.models.User;
import src.components.BorderTextField;
import java.awt.*;

public class UserProfileUI extends JFrame {
    private User activeUser;

    private BorderTextField fullNameField;
    private BorderTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private BorderTextField emailField;

    private JButton saveButton;
    private JButton resetButton;
    private JButton backButton;

    private String fullName;
    private String userName;
    private String password;
    private String email;

    public UserProfileUI (User user, JFrame perviousFrame) 
    {
        setTitle("User Profile");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.email    = user.getEmail();
        this.fullName = user.getFullName();
        this.userName = user.getUsername();
        this.activeUser = user;
        
        JLabel titleLabel = new JLabel("User Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("poppins", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(10, 1, 0, 0));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Full Name:"));
        fullNameField = new BorderTextField(fullName);
        formPanel.add(fullNameField);

        formPanel.add(new JLabel("Username:"));
        usernameField = new BorderTextField(userName);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("New Password:"));
        passwordField = new JPasswordField(password);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Email:"));
        emailField = new BorderTextField(email);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Old password to complete updates: "));
        confirmPasswordField = new JPasswordField(password);
        formPanel.add(confirmPasswordField);


        add(formPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        saveButton  = new FadeButton("Save", new Color(57, 201, 0),new Color(67, 240, 53), Color.WHITE);
        resetButton = new FadeButton("Reset", new Color(71, 120, 200), new Color(71, 120, 230), Color.WHITE);
        backButton  = new FadeButton("Back", new Color(234, 67, 53), new Color(223, 72, 44), Color.WHITE);

        buttonsPanel.add(saveButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(backButton);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(buttonsPanel, BorderLayout.SOUTH);

        // Action Listeners
        saveButton.addActionListener(e -> saveProfile());
        resetButton.addActionListener(e -> resetProfile());
        backButton.addActionListener(e -> {
            perviousFrame.setVisible(true);
            this.dispose();
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(Config.icon.getImage());
    }

    
    private void saveProfile() {

        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String email = emailField.getText().trim();

        // Validate fields 
        if (fullName.isEmpty() || username.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // update user profile data
        boolean updateCompleted = this.activeUser.updateProfile(this.activeUser.getId(), fullName, username, email, password, confirmPassword);

        if (updateCompleted) {
            JOptionPane.showMessageDialog(
                this,  
                "Profile updated successfully !",
                "Message", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        confirmPasswordField.setText("");
        
    }

    private void resetProfile() {
        fullNameField.setText(fullName);
        usernameField.setText(userName);
        passwordField.setText(password);
        emailField.setText(email);
    }

}