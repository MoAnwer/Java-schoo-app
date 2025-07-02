package src.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import src.screens.AboutPage;
import src.screens.BackupFrame;
import src.screens.MainFrame;
import src.screens.UserProfileUI;
import src.models.User;

public class MainMenu extends JMenuBar {
  
    public MainMenu(User activeUser, JFrame frame) {
        JMenu menu = new JMenu("menu");
        JMenu profileMenu = new JMenu("profile");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem homeItem = new JMenuItem("Back to Home");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem backupItem = new JMenuItem("Backup");
        JMenuItem logoutItem = new JMenuItem("logout");
        
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame homeFrame = new MainFrame();
                homeFrame.initialize(activeUser);
                frame.dispose();
            }
        });
    
        
        profileMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                UserProfileUI profileFrame = new UserProfileUI(activeUser, frame);
                frame.dispose();
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
    
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutPage about = new AboutPage();
                about.initialize(frame);
                frame.dispose();
            }
        });
        
        backupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackupFrame backupFrame = new BackupFrame();
                backupFrame.initialize(activeUser);
                frame.dispose();
            }
        });
    
        menu.add(homeItem);
        menu.add(aboutItem);
        menu.add(backupItem);
        menu.add(logoutItem);
    
        menuBar.add(menu);
        menuBar.add(profileMenu);
        
        frame.setJMenuBar(menuBar);
    }

}
