package src.screens;

import src.Config;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import src.components.FadeButton;
import src.components.MainMenu;
import src.models.User;

public class BackupFrame extends JFrame {
    User activeUser;
    String frameName;
    JLabel welcomeLabel;
    Color mainColor = new Color(71, 120, 230);
    Font buttonFont = new Font("poppins", Font.PLAIN, 16);
    private JLabel lastBackupLabel;
    private JButton backupButton;
    private JButton uploadButton;
    private String backupFilePath = "C:/Users/toshiba/Documents/project_ms_3/backup/backup.sql";


    public void initialize(User user)
    {   
        // authenticated user
        this.activeUser = user;
 
        frameName = "Backup";
        
        // Setup Menu
        new MainMenu(this.activeUser, this);
        
        // Setup Frame Properties
        setLayout(new BorderLayout(20, 20));


        JButton backButton = new FadeButton(
            "Backup",
            new Color(71, 120, 200), 
            new Color(71, 120, 230), 
            Color.WHITE
        );
       
        lastBackupLabel = new JLabel("Last Backup at: " + new Date(new File(backupFilePath).lastModified()));
        lastBackupLabel.setBorder(BorderFactory.createEmptyBorder(20, 90, 10, 0));
        lastBackupLabel.setFont(new Font("poppins", Font.PLAIN, 22));

        add(lastBackupLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        backupButton = new FadeButton(
            "Backup now", 
            new Color(71, 120, 200), 
            new Color(71, 120, 230), 
            Color.WHITE
        );

        uploadButton = new FadeButton(
            "Import Backup", 
            new Color(71, 120, 200), 
            new Color(71, 120, 230), 
            Color.WHITE
        );

        buttonPanel.add(backupButton);
        buttonPanel.add(uploadButton);

        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create cmd process to execute mysqldump backup command

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c",
                    "c:/xampp/mysql/bin/mysqldump.exe -u root java_school_app > " + backupFilePath
                );

                try {
                    Process process = pb.start();
                    int exitCode = process.waitFor();

                    // Check if process is success
                    if(exitCode == 0) {
                        JOptionPane.showMessageDialog(null, "Backup  successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Backup failed !!");
                    }
                    
                } catch (java.io.IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

           
        });

        add(buttonPanel, BorderLayout.CENTER);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c",
                    "c:/xampp/mysql/bin/mysql.exe -u root java_school_app < " + backupFilePath 
                );
            
                try {
                    Process process = pb.start();
                    int exitCode = process.waitFor();

                    // Check if process is success
                    if(exitCode == 0) {
                        JOptionPane.showMessageDialog(null, "Backup uploaded successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Backup failed !!");
                    }
                    
                } catch (java.io.IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }

            }
        });

        setBackground(new Color(255, 255, 255));
        setTitle("School System - " + frameName);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        add(backButton, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.CENTER);
        setIconImage(Config.icon.getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }


    

}