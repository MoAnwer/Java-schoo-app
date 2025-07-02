package src.models;

import java.sql.*;
import src.db.DBConnection;

public class Student {
    
    private static int studentsCount;


    /**
     * Add new student in the database
     * @param name
     * @param gender
     * @param address
     * @param className
     * @param stage
     * @param mobile
     * @return
     */
    public static boolean insert(
        String name,
        String gender,
        String address,
        String className,
        String stage,
        String mobile
    ) {

        boolean success = false;

        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("INSERT INTO students (name, gender, address, class, stage, mobile) VALUES (?, ?, ?, ?, ?, ?)");

            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, address);
            statement.setString(4, className);
            statement.setString(5, stage);
            statement.setString(6, mobile);

            statement.execute();

            success = true;

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }


    /** Total students count in school
     * @return int
     */
    public static int studentsCount() 
    {

        try {
            
            Connection con = DBConnection.initialize();

            Statement statement = con.createStatement();
    
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) AS studentsCount FROM students");

            while (resultSet.next()) {
                studentsCount = resultSet.getInt("studentsCount");
            }
            
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return studentsCount;
    }


    /**
     * Update student data
     * @param id
     * @param name
     * @param gender
     * @param address
     * @param className
     * @param stage
     * @param mobile
     * @return boolean
     */
    public static boolean update(
        String id,
        String name,
        String gender,
        String address,
        String className,
        String stage,
        String mobile
    ) {

        boolean success = false;

        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("UPDATE students SET name = ? , gender = ? , address = ? , class = ? , stage = ? , mobile = ? WHERE id = ?");

            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, address);
            statement.setString(4, className);
            statement.setString(5, stage);
            statement.setString(6, mobile);
            statement.setString(7, id);

            statement.executeUpdate();

            success = true;

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }


    /**
     * Delete student from database
     * @param id
     */
    public static void delete(String id)
    {
        
        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("DELETE FROM students WHERE id = ?");

            statement.setString(1, id);

            statement.execute();


            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
