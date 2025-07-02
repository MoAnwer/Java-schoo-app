package src.screens;

import java.awt.*;
import javax.swing.*;
import src.components.FadeButton;
import src.Config;

public class AboutPage extends JFrame {

    public void initialize(JFrame perviousFrame) {
        setFont(new Font("poppins", Font.PLAIN, 16));
        setTitle("School System - About ");
        setSize(700 , 600 );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout ());
        setIconImage(Config.icon.getImage());
        
    
        //Title
        JLabel titleLabel = new JLabel ("About Project" , SwingConstants.CENTER);
        titleLabel.setFont(new Font ("Arial", Font.BOLD , 22));   
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0 , 10,  0));
        add(titleLabel,BorderLayout.NORTH);
    
        //Text
        JTextArea textArea = new JTextArea();
        textArea.setText("The School Management System Project In Java is developed using Java Programming Language, This School Management System Project In Java And MySQL is simple and basic level small project for learning purposes. Also, you can modify this system as per your requirements and develop a perfect advance level project.\n" +
            "A School Management System Java Code allows you to keep the student records and manage them when needed. This is a simple java project with a good and interactive-looking GUI. This Project Use MySQL Database for managing all the data that store in the database.\n\n\n" +
            "Made with ❤ by members: \n - Meshkat Galal \n - Khaled Abouobieda \n - Mohamed Anwer\n\n\n  Computer Sciences Students 💻");
    
        textArea.setFont(new Font ("Segoe UI Symbol" , Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(10, 20, 10 , 20 ));
    
    
        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane, BorderLayout.CENTER);
     
        JButton backButton = new FadeButton("Back", new Color(71, 120, 200), new Color(71, 120, 230), Color.WHITE);
        
        backButton.addActionListener(e -> {
            perviousFrame.setVisible(true);
            this.dispose();
        });
     
        JPanel bottomPanel= new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH );
        setVisible(true);

    }
}
