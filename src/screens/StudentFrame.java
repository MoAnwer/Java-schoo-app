package src.screens;

import src.Config;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import src.models.Student;
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

public class StudentFrame extends JFrame {
      
    User activeUser;
    String frameName;
    JLabel welcomeLabel;
    String selectedStudentId; // this to get the id when user delete student
    JTable table;
    DefaultTableModel model;
    String[] fieldsLabels = {"Student name:", "Gender: ", "Class: ", "Stage: ",  "Address: ", "Mobile number: "};
    JButton insertButton, updateButton, deleteButton, clearButton;
    JLabel nameLabel, genderLabel, addressLabel, classLabel, stageLabel, mobileLabel;
    JTextField nameField, addressField, mobileField;
    JComboBox genderComboBox, classComboBox, stageComboBox;
    JPanel bodyPanel, searchPanel, sidePanel, sideFieldsPanel, actionButtonsPanel;
    TableRowSorter sorter;
    Color mainColor = new Color(26, 115, 232);
    Font buttonFont = new Font("poppins", Font.PLAIN, 16);

    public void initialize(User user)
    {   

        // authenticated user
        this.activeUser = user;
 
        frameName = "Students";
        
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

        this.bodyPanel.setLayout(new BorderLayout(4, 4));
        
        
        setupTable();
        
        fetchStudentsData();

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

        bodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

        bodyPanel.add(searchPanel, BorderLayout.NORTH);
        bodyPanel.add(new JScrollPane(table), BorderLayout.CENTER);

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
        sidePanel.setPreferredSize(new Dimension(300, 700));
        sidePanel.setBackground(mainColor);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

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

        actionButtonsPanel.setBackground(mainColor);
        actionButtonsPanel.setLayout(new GridLayout(2, 1, 4, 5));

        insertButton  = new FadeButton("Insert", new Color(255, 140, 0), new Color(252, 110, 0), Color.WHITE);
        insertButton.setPreferredSize(new Dimension(130, 35));

        clearButton   = new FadeButton("Clear", new Color(89, 89, 89), new Color(120, 112, 118), Color.WHITE);
        clearButton.setPreferredSize(new Dimension(130, 35));

        updateButton  = new FadeButton("Update", new Color(57, 201, 0),new Color(67, 240, 53), Color.WHITE);
        updateButton.setPreferredSize(new Dimension(130, 35));

        deleteButton  = new FadeButton("Delete", new Color(234, 67, 53), new Color(223, 72, 44), Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(130, 35));
        
        
        // Insert Action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertStudentData(
                    nameField.getText(),
                    genderComboBox.getSelectedItem().toString(),
                    addressField.getText(),
                    classComboBox.getSelectedItem().toString(),
                    stageComboBox.getSelectedItem().toString(),
                    mobileField.getText()
                );
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateStudentData(
                    selectedStudentId,
                    nameField.getText(),
                    genderComboBox.getSelectedItem().toString(),
                    addressField.getText(),
                    classComboBox.getSelectedItem().toString(),
                    stageComboBox.getSelectedItem().toString(),
                    mobileField.getText()
                );
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudentData(selectedStudentId);
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
        addressField   = new BorderTextField(100, 10, Color.WHITE);
        mobileField    = new BorderTextField(100, 10, Color.WHITE);
        genderComboBox = new JComboBox(new String[] {"male", "female"});
        genderComboBox.setPreferredSize(new Dimension(100, 20));
        classComboBox = new JComboBox(new String[] {"1", "2", "3", "4", "5", "6"});
        classComboBox.setPreferredSize(new Dimension(100, 20));
        stageComboBox = new JComboBox(new String[] {"basic", "middle", "secondray"});
        stageComboBox.setPreferredSize(new Dimension(100, 20));
        


        // Add Fields to panel
        nameLabel    = new Label(fieldsLabels[0]);
        genderLabel  = new Label(fieldsLabels[1]);
        classLabel   = new Label(fieldsLabels[2]);
        stageLabel   = new Label(fieldsLabels[3]);
        addressLabel = new Label(fieldsLabels[4]);
        mobileLabel  = new Label(fieldsLabels[5]);

        sideFieldsPanel.setLayout(new GridLayout(12, 2, 15, 15));
        sideFieldsPanel.setBackground(mainColor);
        
        sideFieldsPanel.add(nameLabel);
        sideFieldsPanel.add(nameField);

        sideFieldsPanel.add(genderLabel);
        sideFieldsPanel.add(genderComboBox);

        sideFieldsPanel.add(classLabel);
        sideFieldsPanel.add(classComboBox);

        sideFieldsPanel.add(stageLabel);
        sideFieldsPanel.add(stageComboBox);
        
        sideFieldsPanel.add(addressLabel);
        sideFieldsPanel.add(addressField);
        
        sideFieldsPanel.add(mobileLabel);
        sideFieldsPanel.add(mobileField);

        sidePanel.add(sideFieldsPanel, BorderLayout.NORTH);

    }


    /**
     * get users from database & show users data in data table
     */
    public void fetchStudentsData()
    {
        Thread fetchStudentsThread = new Thread(() -> {
            try {
        
                Connection con = DBConnection.initialize();
    
                Statement statement = con.createStatement();
    
    
                ResultSet resultSet = statement.executeQuery("SELECT id, name, gender, class, stage, address, mobile FROM students ORDER BY id desc");
    
                // Clear all data in table to update data in it
                model.setRowCount(0);
    
                while(resultSet.next())
                {
                    model.addRow(new Object[] {
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("gender"),
                        resultSet.getString("class"),
                        resultSet.getString("stage"),
                        resultSet.getString("address"),
                        resultSet.getString("mobile"),
                    });
                }
    
                con.close();
    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage(), "Error", JOptionPane.OK_OPTION);
            }
        });

        fetchStudentsThread.start();
    }

    /**
     * Insert user data to database
     * @param data
     */
    public void insertStudentData(String name, String gender, String address, String className, String stage, String mobile)
    {
        // Check if there is empty field
        if (!name.equals("") && !mobile.equals("")) 
        {    
            Student.insert(name, gender, address, className, stage, mobile);

            // show updated data after insert
            fetchStudentsData();            
            // reset fields 
            clearFields();

            JOptionPane.showMessageDialog(
                    null,  
                    "Student " + name + " added successfully !",
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {
            JOptionPane.showMessageDialog(null,  "Please fill name and mobile fields and try again", "Error", JOptionPane.OK_OPTION);
        }
    }

    /*
     *  updated selected student data
     */
    public void updateStudentData(String id, String name, String gender, String address, String className, String stage, String mobile)
    {
        // Check if there is selected student
        if (selectedStudentId != null) {
            // Check if there is empty field
            if (!name.equals("") && !mobile.equals("")) 
            {    
                Student.update(id, name, gender, address, className, stage, mobile);
    
                // show updated data after insert
                fetchStudentsData();            
                // reset fields 
                clearFields();

                JOptionPane.showMessageDialog(
                    null,  
                    "Student updated successfully !",
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                JOptionPane.showMessageDialog(null,  "Please fill name and mobile fields and try again", "Error", JOptionPane.OK_OPTION);
            }

        } else {
            JOptionPane.showMessageDialog(
                null,  
                "Please select student first and try again",
                "Message", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    /**
     * delete student from database
     * @param id
     */
    public void deleteStudentData(String id)
    {
        try {

            if (id != null) 
            {
                // Confirm delete action
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure delete student with id #" + id + "?", "Delete Confirmation", JOptionPane.OK_CANCEL_OPTION);

                if (confirm == JOptionPane.OK_OPTION) 
                {
                    // delete student from database
                    Student.delete(id);
                }
               
                // update students list
                fetchStudentsData();

                clearFields();

            } else {
                JOptionPane.showMessageDialog(null, "Please select student first and try again", "Message", JOptionPane.OK_OPTION);
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
        model.addColumn("gender");
        model.addColumn("class");
        model.addColumn("stage");
        model.addColumn("address");
        model.addColumn("mobile");
    
        // Handle action when user click on row or cell on row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // get clicked row
                int row = table.getSelectedRow();

                // check if the clicked element is row 
                if (row != -1) {

                    selectedStudentId = model.getValueAt(row, 0).toString();

                    nameField.setText(model.getValueAt(row, 1).toString());
                    addressField.setText(model.getValueAt(row, 5).toString());
                    mobileField.setText(model.getValueAt(row, 6).toString());
                    genderComboBox.setSelectedItem(model.getValueAt(row, 2));
                    classComboBox.setSelectedItem(model.getValueAt(row, 3));
                    stageComboBox.setSelectedItem(model.getValueAt(row, 4));
                }
            }
        });

    }
    
    public void clearFields()
    {
        nameField.setText("");
        addressField.setText("");
        mobileField.setText("");
    
        selectedStudentId = null;
    }


}