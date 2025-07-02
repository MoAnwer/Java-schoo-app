package src.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.lang.ClassNotFoundException;


public class DBConnection 
{
    // connection to mysql database config
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/java_school_app";
    protected static final String DB_USERNAME = "root";
    
    public static Connection initialize()
    {

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            
            // connect with mysql database
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, "");

            System.out.println("connection establish...");
            
        } catch (ClassNotFoundException e) {

            JOptionPane.showMessageDialog(null, "Database Error : " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Database Error : " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return connection;
        
    }
}
