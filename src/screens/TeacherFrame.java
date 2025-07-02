package src.screens;

import src.Config;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import src.models.Teacher;
import src.models.User;
import src.db.DBConnection;
import src.components.Label;
import src.components.MainMenu;
import src.components.FadeButton;
import src.components.BorderTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class TeacherFrame extends JFrame {
    User activeUser;
    String frameName;
    JLabel welcomeLabel;
    String selectedTeacherId; // this to get the id from JTable when user delete Teacher
    JTable table;
    DefaultTableModel model;
    JButton insertButton, updateButton, deleteButton, clearButton;
    Label nameLabel, salaryLabel, searchLabel, addressLabel, subjectLabel, stageLabel, phoneLabel;
    JTextField nameField, searchField, salaryField, phoneField, subjectField, addressField;
    JPanel bodyPanel, searchPanel, sidePanel, sideFieldsPanel, actionButtonsPanel;
    Color mainColor = new Color(71, 120, 230);
    TableRowSorter sorter;
    Font buttonFont = new Font("poppins", Font.PLAIN, 16);

    public void initialize(User user)
    {   
        // authenticated user
        this.activeUser = user;
 
        frameName = "Teachers";
        
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
        super.add(setupSidePanel(), BorderLayout.WEST);
        super.add(this.setupBodyPanel(), BorderLayout.CENTER);

    }

    public JPanel setupBodyPanel()
    {
        this.bodyPanel = new JPanel();

        searchPanel = new JPanel();

        searchPanel.setLayout(new BorderLayout(5, 5));

        
        Label searchLabel = new Label("Search :", Color.BLACK, 15);
        
        BorderTextField searchField = new BorderTextField(
            300, 
            36,
            new Color(71, 120, 230), 
            Color.BLACK
        );
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText().trim()));
            }
        });
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        this.bodyPanel.setLayout(new BorderLayout(4, 4));

        this.bodyPanel.add(searchPanel, BorderLayout.NORTH);
        
        setupTable();
        
        fetchTeachersData();

        bodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        bodyPanel.add(new JScrollPane(table));

        // Add this.welcomePanel into content pane of screen
        return this.bodyPanel;
    }

    /**
     *  Setup Side panel
     * @return sidePanel
     */
    public JPanel setupSidePanel()
    {
        sidePanel       = new JPanel();
        sideFieldsPanel = new JPanel();

        // Setup Width & Background of side panel
        sidePanel.setPreferredSize(
            new Dimension(300, 700)
        );

        sidePanel.setBackground( mainColor );

        sidePanel.setBorder(
            BorderFactory.createEmptyBorder(
                20, 
                10, 
                10, 
                10
            )
        );

        // Set Width of side panel buttons
        sidePanel.setLayout(
            new BorderLayout(
                140, 
                1
            )
        );
        
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

        actionButtonsPanel.setBackground(mainColor);
        actionButtonsPanel.setLayout(
            new GridLayout(
                2, 
                1, 
                4, 
                5
            )
        );

        insertButton  = new FadeButton(
            "Insert", 
            new Color(255, 140, 0), 
            new Color(252, 110, 0), 
            Color.WHITE
        );

        insertButton.setPreferredSize(
            new Dimension(
                130, 
                35
            )
        );

        clearButton  = new FadeButton(
            "Clear", 
            new Color(89, 89, 89), 
            new Color(120, 112, 118),
            Color.WHITE
        );

        clearButton.setPreferredSize(new Dimension(130, 35));

        updateButton  = new FadeButton(
            "Update", 
            new Color(57, 201, 0),
            new Color(67, 240, 53), 
            Color.WHITE
        );

        updateButton.setPreferredSize(new Dimension(130, 35));

       deleteButton  = new FadeButton("Delete", new Color(234, 67, 53), new Color(223, 72, 44), Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(130, 35));
        
        
        // Insert Action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertTeacherData(
                    nameField.getText(),
                    salaryField.getText(),
                    addressField.getText(),
                    subjectField.getText(),
                    phoneField.getText()
                );
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateTeacherData(
                    selectedTeacherId,
                    nameField.getText(),
                    salaryField.getText(),
                    addressField.getText(),
                    subjectField.getText(),
                    phoneField.getText()
                );
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTeacherData(selectedTeacherId);
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
        actionButtonsPanel.add(updateButton);
        actionButtonsPanel.add(deleteButton);
        actionButtonsPanel.add(clearButton);
        sidePanel.add(actionButtonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Setup name, password, gender fields in side panel with custom style
     * @return void
     */

    public void setupFieldsPanel()
    {
        // Add Fields
        nameField      = new BorderTextField(100, 10, Color.WHITE);
        salaryField   = new BorderTextField(100, 10, Color.WHITE);
        subjectField    = new BorderTextField(100, 10, Color.WHITE);
        phoneField    = new BorderTextField(100, 10, Color.WHITE);
        addressField    = new BorderTextField(100, 10, Color.WHITE);

        // Add Fields to panel
        nameLabel    = new Label("Teacher Name: ");
        salaryLabel  = new Label("Salary: ");
        subjectLabel   = new Label("Subject : ");
        addressLabel = new Label("Address :");
        phoneLabel  = new Label("Phone : ");

        sideFieldsPanel.setLayout(new GridLayout(12, 2, 15, 15));
        sideFieldsPanel.setBackground(mainColor);
        
        sideFieldsPanel.add(nameLabel);
        sideFieldsPanel.add(nameField);
        
        sideFieldsPanel.add(subjectLabel);
        sideFieldsPanel.add(subjectField);

        sideFieldsPanel.add(salaryLabel);
        sideFieldsPanel.add(salaryField);

        sideFieldsPanel.add(addressLabel);
        sideFieldsPanel.add(addressField);
        
        sideFieldsPanel.add(phoneLabel);
        sideFieldsPanel.add(phoneField);

        sidePanel.add(sideFieldsPanel, BorderLayout.NORTH);

    }


    /**
     * select users from database & show users data in data table
     */
    public void fetchTeachersData()
    {
              
        try {
            
            Connection con = DBConnection.initialize();

            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, name, subject, salary, address, phone FROM teachers ORDER BY id desc");

            // Delete all data in table
            model.setRowCount(0);

            while(resultSet.next())
            {
                model.addRow(new Object[] {
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("subject"),
                    resultSet.getString("salary"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                });
            }

            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage(), "Error", JOptionPane.OK_OPTION);
        }
    }



    /**
     * Insert user data to database
     * @param data
     */
    public void insertTeacherData(String name, String salary, String address, String subject, String phone)
    {
        // Check if there is empty field
        if (!name.equals("") && !phone.equals("") && !subject.equals("") && !salary.equals("")) 
        {    
            boolean insetCompleted  = Teacher.insert(name, salary, subject, phone, address);
          
            // show updated data after insert
            fetchTeachersData();            
            // reset fields 
            clearFields();

            // show success message
            if (insetCompleted) {
                JOptionPane.showMessageDialog(
                    null,  
                    "Teacher added successfully !",
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                null,  
                "Please fill name, salary and phone fields and try again",
                "Error", 
                JOptionPane.OK_OPTION
            );
        }
    }



    /*
     *  updated selected Teacher data
     */
    public void updateTeacherData(String id, String name, String salary, String address, String subject, String phone)
    {

        // Check if there is selected teacher
        if (selectedTeacherId != null) {

            // Check if there is empty field
            if (!name.equals("") && !phone.equals("") && !subject.equals("") && !salary.equals("")) 
            {   
                boolean updateCompleted = Teacher.update(id, name, salary, subject, phone, address);

                // show updated data after insert
                fetchTeachersData();            
                // reset fields 
                clearFields();

                // show success message
                if (updateCompleted) {
                    JOptionPane.showMessageDialog(
                        null,  
                        "Teacher " + name + " updated successfully !",
                        "Message", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } else {
                JOptionPane.showMessageDialog(
                    null, 
                    "Please fill name and phone fields and try again",
                    "Message", 
                    JOptionPane.OK_OPTION
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                null, 
                "Please select teacher first and try again",
                "Message", 
                JOptionPane.OK_OPTION
            );
        }
    }


    /**
     * delete Teacher from database
     * @param id
     */
    public void deleteTeacherData(String id)
    {
        try {

            // Check if there is selected teacher
            if (id != null) 
            {
                // Confirm delete action
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure delete Teacher with id #" + id + "?", "Delete Confirmation", JOptionPane.OK_CANCEL_OPTION);

                if (confirm == JOptionPane.OK_OPTION) 
                {
                    // delete Teacher from database
                    Teacher.delete(id);
                          
                      // update Teachers list
                    fetchTeachersData();

                    clearFields();

                    // show success message
                    JOptionPane.showMessageDialog(
                        null,  
                        "Teacher added successfully !",
                        "Message", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
               
              

            } else {
                JOptionPane.showMessageDialog(null, "Please select Teacher first and try again", "Message", JOptionPane.OK_OPTION);
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

        sorter = new TableRowSorter<>(model);
        sorter.setSortsOnUpdates(false);

        table.setRowSorter(sorter);

        // titles of the head table columns
        model.addColumn("id");
        model.addColumn("name");
        model.addColumn("subject");
        model.addColumn("salary");
        model.addColumn("address");
        model.addColumn("phone");
    
        // Handle action when user click on row or cell on row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // get clicked row
                int row = table.getSelectedRow();

                // check if the clicked element is row 
                if (row != -1) {

                    selectedTeacherId = model.getValueAt(row, 0).toString();

                    nameField.setText(model.getValueAt(row, 1).toString());
                    subjectField.setText(model.getValueAt(row, 2).toString());
                    salaryField.setText(model.getValueAt(row, 3).toString());
                    addressField.setText(model.getValueAt(row, 4).toString());
                    phoneField.setText(model.getValueAt(row, 5).toString());
                }
            }
        });

    }
    
    public void clearFields()
    {
        nameField.setText("");
        salaryField.setText("");
        subjectField.setText("");
        addressField.setText("");
        phoneField.setText("");

        selectedTeacherId = null;
    }
}