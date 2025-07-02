package src.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import src.db.DBConnection;

public class User {

    public int id;
    public String fullName;
    public String username;
    public String email;
    private String password;

    public User(int id, String fullName, String username, String email, String password)
    {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public int getId() 
    {
        return id;
    }

    public String getUsername() 
    {
        return this.username;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getFullName() 
    {
        return fullName;
    }

    /**
     * Insert new user to database
     * @param fullName
     * @param username
     * @param email
     * @param password
     * @return boolean success
     */
    public static boolean insert(String fullName, String username, String email, String password)
    {
        boolean success = false;
    
        try {
            Connection con = DBConnection.initialize();
            PreparedStatement statement = con.prepareStatement("INSERT INTO users (full_name, username, email, password) VALUES (?, ?, ?, ?)");

            statement.setString(1, String.valueOf(fullName));
            statement.setString(2, String.valueOf(username));
            statement.setString(3, String.valueOf(email));
            statement.setString(4, String.valueOf(password));
            statement.executeUpdate();

            success = true;
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return success;
    }


    /**
     * Update user profile data
     * @param id
     * @param fullName
     * @param username
     * @param email
     * @param password
     * @param confirmPassword
     * @return boolean success
     */
    public boolean updateProfile(int id, String fullName, String username, String email, String password, String confirmPassword) 
    {
        boolean success = false;

        try {

            if(confirmPassword.equals(this.password)) {
                Connection con = DBConnection.initialize();
        
                PreparedStatement statement = con.prepareStatement("UPDATE users SET full_name = ?, username = ? , email = ?, password = ? WHERE id = ?");

                statement.setString(1, String.valueOf(fullName));
                statement.setString(2, String.valueOf(username));
                statement.setString(3, String.valueOf(email));
                statement.setString(4, String.valueOf(password));
                statement.setInt(5, id);

                this.fullName = fullName;
                this.username = username;
                this.email = email;
                
                statement.executeUpdate();

                success = true;

                con.close();

            } else {

                JOptionPane.showMessageDialog(
                null,  
                "The entered password is incorrect !!",
                "Message", 
                JOptionPane.OK_OPTION
            );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,  
                e.getMessage(),
                "Message", 
                JOptionPane.OK_OPTION
            );
        }

        return success;
    }


    
    public static int usersCount() {

        int usersCount = 0;

        try {

            Connection con = DBConnection.initialize();

            Statement statement = con.createStatement();
    
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) AS usersCount FROM users");

            while (resultSet.next()) {
                usersCount = resultSet.getInt("usersCount");
            }
            
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return usersCount;
    }


    /**
     * Delete user by email
     * @param email
     */
    public static void delete(String email)
    {
        try {

            Connection con = DBConnection.initialize();
            PreparedStatement statement = con.prepareStatement("DELETE FROM users WHERE email = ? ");
            statement.setString(1, email);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        }
    }

}
