package src.screens;

import src.Config;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import src.components.RoundBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.components.FadeButton;
import src.models.User;
import src.models.Student;
import src.models.Teacher;

public class MainFrame extends JFrame 
{
 
    User activeUser;
    String frameName;
    JLabel welcomeLabel;
    String[] sideButtonsLabels = {"Accounts", "Students", "Teachers", "Backup", "About", "Profile", "Logout"};
    JButton[] sideButtons;
    JPanel welcomePanel, sidePanel, sideButtonsPanel;
    JFrame frame;

    public void initialize(User user)
    {
        
        this.activeUser = user;

        frame = this; // this help me in initialize about frame

        setFrameName();
        
        // Setup Frame Panels

        // Setup Frame Properties
        setLayout(new BorderLayout(1, 1));
        setTitle("School System - " + frameName);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(Config.icon.getImage());
        

        // Add sidePanel into content pane of screen
        super.add(this.setupSidePanel(), BorderLayout.WEST);
        super.add(this.setupWelcomePanel(), BorderLayout.CENTER);

    }

    public JPanel setupCardsPanel() {
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        cardsPanel.setBorder(new EmptyBorder(20, 30, 20, 30)); 
        cardsPanel.setBackground(new Color(240, 240, 240));        

        welcomeLabel = new JLabel(
            "Dashboard",
            JLabel.LEFT
        );

        welcomeLabel.setFont(new Font("poppins", Font.BOLD, 32));

        cardsPanel.add(welcomeLabel, SwingUtilities.CENTER);
        cardsPanel.add(new JLabel());
        cardsPanel.add(new JLabel());
        cardsPanel.add(createCard(Student.studentsCount(), "Student in school"));
        cardsPanel.add(createCard(Teacher.teachersCount(), "Teacher in school"));
        cardsPanel.add(createCard(User.usersCount(), "User in system"));
      

        return cardsPanel;
    }

    /**
     * Create information cards to show some stats (students count)
     * @return
     */
    public  JPanel createCard(int number, String title) {
        
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(15, new Color(200, 200, 200)), 
                new EmptyBorder(12, 12, 12, 12) 
        ));

        JLabel numberLabel = new JLabel(String.valueOf(number));
        numberLabel.setFont(new Font("poppins", Font.BOLD, 36));
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberLabel.setForeground(new Color(40, 40, 255));
        numberLabel.setForeground(new Color(50, 50, 50));

    
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("poppins", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setForeground(new Color(70, 70, 70));



        card.add(numberLabel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));

        return card;
    }



    public void setFrameName()
    {
        frameName = "Home";
    }

    public JPanel setupWelcomePanel()
    {
        this.welcomePanel = new JPanel(); 

        this.welcomePanel.setLayout(
            new GridLayout(2, 1, 10, 10)
        );

        this.welcomePanel.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

       
        this.welcomePanel.add(setupCardsPanel(), BorderLayout.NORTH);
        // Add this.welcomePanel into content pane of screen
        return this.welcomePanel;
    }

    public JPanel setupSidePanel()
    {
      
        sidePanel        = new JPanel();
        sideButtonsPanel = new JPanel();
        
        // Setup Width & Background of side panel
        sidePanel.setPreferredSize(
            new Dimension(300, 700)
        );

        sidePanel.setBackground(
            new Color(44, 44, 44)
        );

        sidePanel.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        // Set Width of side panel buttons
        sideButtons = new JButton[7];

        sidePanel.setLayout(
            new BorderLayout(140, 1)
        );

        // Set Vertical layout with columns = sideButtons length
        sideButtonsPanel.setLayout(
            new GridLayout(
                sideButtons.length, 
                1, 
                15, 
                10
            )
        );

        sideButtonsPanel.setBackground(
            new Color(44, 44, 44)
        );

        // Handling actions of side panel buttons
        for (int i = 0; i < sideButtons.length; i++) 
        {
            sideButtons[i] = new FadeButton(
                sideButtonsLabels[i], 
                new Color(66, 66, 66), 
                new Color(77, 77, 77), 
                new Color(255, 255, 255)
            );

            // Set size of button
            sideButtons[i].setPreferredSize(new Dimension(270, 35));

            // Show applications Pages on click on buttons
            sideButtons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (e.getSource() == sideButtons[0]) 
                    {
                        AccountsFrame accountsFrame = new AccountsFrame();
                        accountsFrame.initialize(activeUser);
                        dispose();
                    } 

                    else if (e.getSource() == sideButtons[1]) 
                    {
                        StudentFrame studentFrame = new StudentFrame();
                        studentFrame.initialize(activeUser);
                        dispose();
                    }

                    else if (e.getSource() == sideButtons[2]) 
                    {
                        TeacherFrame teacherFrame = new TeacherFrame();
                        teacherFrame.initialize(activeUser);
                        dispose();
                    }

                    else if (e.getSource() == sideButtons[3]) 
                    {
                        BackupFrame backupFrame = new BackupFrame();
                        backupFrame.initialize(activeUser);
                        dispose();
                    }

                    else if (e.getSource() == sideButtons[4]) 
                    {
                        AboutPage about = new AboutPage();
                        about.initialize(frame);
                        dispose();                    
                    }

                    else if (e.getSource() == sideButtons[5]) 
                    {
                       UserProfileUI profile = new UserProfileUI(activeUser, frame);
                    }

                    else if (e.getSource() == sideButtons[6]) 
                    {
                        JOptionPane.showMessageDialog(
                            null,  
                            "Thank you for use our system",
                            "Message", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        
                       System.exit(0);
                    }
                };
            });

            // Add current button into buttons Panel on side panel
            sideButtonsPanel.add(sideButtons[i]);

            sideButtonsPanel.setSize(new Dimension(100, 150));
        }

        sidePanel.add(sideButtonsPanel, BorderLayout.NORTH);

        return sidePanel;
    }
    
} 