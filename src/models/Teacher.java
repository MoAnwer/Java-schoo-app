package src.models;

import java.sql.*;
import src.db.DBConnection;

public class Teacher 
{

    /**
     * Add new teacher in database 
     * @param name
     * @param salary
     * @param subject
     * @param phone
     * @param address
     * @return
     */    
    public static boolean insert(
        String name,
        String salary,
        String subject,
        String phone,
        String address
    ) {

        boolean success = false;

        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("INSERT INTO teachers (name, salary, subject, phone, address) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, name);
            statement.setString(2, salary);
            statement.setString(3, subject);
            statement.setString(4, phone);
            statement.setString(5, address);

            statement.execute();

            success = true;

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }


    
    public static int teachersCount() {

        int teachersCount = 0;

        try {

            Connection con = DBConnection.initialize();

            Statement statement = con.createStatement();
    
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) AS teachersCount FROM teachers");

            while (resultSet.next()) {
                teachersCount = resultSet.getInt("teachersCount");
            }
            
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return teachersCount;
    }


    /**
     * Update teacher data
     * @param id
     * @param name
     * @param salary
     * @param subject
     * @param phone
     * @param address
     * @return
     */
    public static boolean update(
        String id,
        String name,
        String salary,
        String subject,
        String phone,
        String address
    ) {

        boolean success = false;

        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("UPDATE teachers SET name = ? , salary = ? , subject = ? , phone = ?, address = ?  WHERE id = ?");

            statement.setString(1, name);
            statement.setString(2, salary);
            statement.setString(3, subject);
            statement.setString(4, phone);
            statement.setString(5, address);
            statement.setString(6, id);

            statement.execute();

            success = true;

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }

    /**
     * Delete teacher from database
     * @param id
     */
    public static void delete(String id)
    {
        
        try {
            
            Connection con = DBConnection.initialize();

            PreparedStatement statement = con.prepareStatement("DELETE FROM teachers WHERE id = ?");

            statement.setString(1, id);

            statement.execute();


            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
