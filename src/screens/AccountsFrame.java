package src.screens;

import src.Config;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import src.models.User;
import src.db.DBConnection;
import src.components.Label;
import src.components.MainMenu;
import src.components.FadeButton;
import src.components.BorderTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class AccountsFrame extends JFrame {
   
    User activeUser;
    String frameName;
    JLabel welcomeLabel;
    String selectedUserId; // this to get the id from JTable when user delete Teacher
    JTable table;
    DefaultTableModel model;
    String[] fieldsLabels = {"Username:", "Email: ", "Password: ", "Full Name: "};
    JButton insertButton, updateButton, deleteButton, clearButton;
    JLabel fullNameLabel, usernameLabel, emailLabel, passwordLabel;
    JTextField fullNameField, userNameField, emailField;
    JPasswordField passwordField;
    JPanel bodyPanel, sidePanel, sideFieldsPanel, actionButtonsPanel;
    Color mainColor = new Color(71, 120, 230);
    Font buttonFont = new Font("poppins", Font.PLAIN, 16);

    public void initialize(User user)
    {   
        // authenticated user
        this.activeUser = user;
 
        frameName = "Accounts";
        
        // Setup Menu
        new MainMenu(this.activeUser, this);
        
        // Setup Frame Properties
        setLayout(new BorderLayout(1, 1));
        setBackground(new Color(255, 255, 255));
        setTitle("School System - " + frameName);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(Config.icon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Add sidePanel into content pane of screen
        super.add(this.setupSidePanel(), BorderLayout.WEST);
        super.add(this.setupBodyPanel(), BorderLayout.CENTER);

    }

    public JPanel setupBodyPanel()
    {
        this.bodyPanel = new JPanel();

        this.bodyPanel.setLayout(new GridLayout(0, 1, 10, 10));
        
        setupTable();
        
        fetchUsersData();

        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        bodyPanel.add(new JScrollPane(table));

        // Add this.welcomePanel into content pane of screen
        return this.bodyPanel;
    }

    public JPanel setupSidePanel()
    {
        sidePanel       = new JPanel();
        sideFieldsPanel = new JPanel();

        // Setup Width & Background of side panel
        sidePanel.setPreferredSize(new Dimension(300, 700));
        sidePanel.setBackground(mainColor);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));

        // Set Width of side panel buttons
        sidePanel.setLayout(new BorderLayout(140, 1));
        
        setupFieldsPanel();
        
        setupActionsButtonsPanel();

        return sidePanel;

    }

    /**
     * Setup actions buttons panel that handle insert, update, delete, clear actions on side panel & handle actions of them
     * @return void
     */
    public void setupActionsButtonsPanel()
    {
        actionButtonsPanel = new JPanel();

        actionButtonsPanel.setLayout(new GridLayout(2, 2, 10, 5));
        actionButtonsPanel.setBackground(mainColor);

      insertButton  = new FadeButton("Insert", new Color(255, 140, 0), new Color(252, 110, 0), Color.WHITE);
        clearButton   = new FadeButton("Clear", new Color(89, 89, 89), new Color(120, 112, 118), Color.WHITE);
       deleteButton  = new FadeButton("Delete", new Color(234, 67, 53), new Color(223, 72, 44), Color.WHITE);
        
        
        // Insert Action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertUserData(
                    fullNameField.getText().trim(), 
                    userNameField.getText().trim(), 
                    emailField.getText().trim(), 
                    String.valueOf(passwordField.getPassword()).trim()
                );
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUserData(emailField.getText().trim());
            }
        });

        
        // Clear Action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        actionButtonsPanel.add(insertButton);
        actionButtonsPanel.add(deleteButton);
        actionButtonsPanel.add(clearButton);
        sidePanel.add(actionButtonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Setup username, password, email fields in side panel with custom style
     * @return void
     */

    public void setupFieldsPanel()
    {
        // Add Fields
        fullNameField = new BorderTextField();
        emailField    = new BorderTextField();
        userNameField = new BorderTextField();
        
        passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(233, 244, 255)));
        passwordField.setForeground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(100, 25));


        // Add Fields to panel
        usernameLabel = new Label(fieldsLabels[0]);
        emailLabel    = new Label(fieldsLabels[1]);
        passwordLabel = new Label(fieldsLabels[2]);
        fullNameLabel = new Label(fieldsLabels[3]);

        sideFieldsPanel.setLayout(new GridLayout(9, 3, 52, 5));
        sideFieldsPanel.setBackground(mainColor);

        Label sideLabel = new Label("Users Accounts", Color.WHITE, 26);
        sideLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        sideFieldsPanel.add(sideLabel);

        sideFieldsPanel.add(fullNameLabel);
        sideFieldsPanel.add(fullNameField);
        sideFieldsPanel.add(usernameLabel);
        sideFieldsPanel.add(userNameField);
        sideFieldsPanel.add(emailLabel);
        sideFieldsPanel.add(emailField);
        sideFieldsPanel.add(passwordLabel);
        sideFieldsPanel.add(passwordField);

        sidePanel.add(sideFieldsPanel, BorderLayout.NORTH);


    }


    /**
     * select users from database & show users data in data table
     */
    public void fetchUsersData()
    {
              
        Thread fetchUsersThread = new Thread (() -> {
           
            try {
            
                Connection con = DBConnection.initialize();
    
                PreparedStatement statement = con.prepareStatement("SELECT id, full_name, username, email FROM users WHERE email != ? ORDER BY id desc");
    
                statement.setString(1, this.activeUser.getEmail());
    
                ResultSet resultSet = statement.executeQuery();
    
                // Delete all data in table
                model.setRowCount(0);
    
                while(resultSet.next())
                {
                    model.addRow(new Object[] {
                        resultSet.getString("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("username"),
                        resultSet.getString("email")
                    });
                }
    
                con.close();
    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage(), "Error", JOptionPane.OK_OPTION);
            }
            
        });

        fetchUsersThread.start();
    }

    /**
     * Insert user data to database
     * @param data
     */
    public void insertUserData(String fullName, String username, String email, String password)
    {
        // Check if there is empty field
        if (!fullName.equals("") && !username.equals("") && !email.equals("") && !password.equals("")) 
        {    
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(
                    null,  
                    "Password must be contains 6 characters at least",
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                boolean insertSuccess =  User.insert(fullName, username, email, password);

                if (!insertSuccess) {
                    JOptionPane.showMessageDialog(null,  "inserted  username or email already exists", "Error", JOptionPane.OK_OPTION);
                } else {
                    // show updated data after insert
                    fetchUsersData();            
                    // reset fields 
                    clearFields();
    
                    JOptionPane.showMessageDialog(
                        null,  
                        "User added successfully !",
                        "Message", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }

            }
                

        } else {
            JOptionPane.showMessageDialog(null,  "Please fill all fields and try again", "Error", JOptionPane.OK_OPTION);
        }


    }

    /**
     * delete user from database
     * @param email
     */
    public void deleteUserData(String email)
    {
        try {

            // if there no selected user form data table
            if (selectedUserId != null) {

            // Confirm delete action
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure delete user with email " + email + "?", "Delete Confirmation", JOptionPane.OK_CANCEL_OPTION);

                if (confirm == JOptionPane.OK_OPTION) {
                    // delete user from database
                    User.delete(email);

                    // update users list
                    fetchUsersData();

                    clearFields();

                    JOptionPane.showMessageDialog(
                        null,  
                        "User deleted successfully !",
                        "Message", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
                
            } else {
                JOptionPane.showMessageDialog(
                    null,  
                    "Please select user first and try again",
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
    }

    /**
     *  Setup table with its components and actions
     */
    public void setupTable() 
    {
        model = new DefaultTableModel() {
            // Disable cell editing when click it
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);

        // titles of the head table columns
        model.addColumn("id");
        model.addColumn("Full name");
        model.addColumn("Username");
        model.addColumn("Email");
    
        // Handle action when user click on row or cell on row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // get clicked row
                int row = table.getSelectedRow();

                // check if the clicked element is row 
                if (row != -1) {

                    selectedUserId = model.getValueAt(row, 0).toString();


                    // Fill fields by row cells data
                    fullNameField.setText(model.getValueAt(row, 1).toString());
                    userNameField.setText(model.getValueAt(row, 2).toString());
                    emailField.setText(model.getValueAt(row, 3).toString());
                }
            }
        });

    }
    
    public void clearFields()
    {
        fullNameField.setText("");
        userNameField.setText("");
        emailField.setText("");
        passwordField.setText("");

        selectedUserId = null;
    }
}
