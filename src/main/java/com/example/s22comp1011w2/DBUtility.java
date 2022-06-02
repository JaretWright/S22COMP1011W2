package com.example.s22comp1011w2;

import java.sql.*;

public class DBUtility {
    private static String user = DBCrendentials.user;
    private static String password = DBCrendentials.password;

    //127.0.0.1 = where the MySQL server is
    //3306 = the port where MySQL server is accessible
    //S22COMP1011 =  the database name
    private static String connectURL = "jdbc:mysql://127.0.0.1:3306/S22COMP1011";

    /**
     * This method will insert the Phone object into the Database table named "phones"
     */
    public static int insertPhoneIntoDB(Phone phone) throws SQLException {
        int phoneID = -1;

        //this will hold the id (key field) value created in the DB
        ResultSet resultSet = null;

        String sql = "INSERT INTO phones (make, model, os, ram, backCameraMP, price, batteryLifeInHours, quantityInStock) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        //this is called a try with resources
        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})
                ) {

            //We use the PreparedStatement to reduce the risk of a sql injection attack.
            ps.setString(1, phone.getMake());
            ps.setString(2,phone.getModel());
            ps.setString(3, phone.getOs());
            ps.setInt(4, phone.getRam());
            ps.setInt(5,phone.getBackCameraMP());
            ps.setDouble(6,phone.getPrice());
            ps.setInt(7,phone.getBatteryLifeInHours());
            ps.setInt(8,phone.getQuantityInStock());

            //now that the paramters have been added for the phone, we can execute the command
            ps.executeUpdate();

            //this returns the phone id in the database
            resultSet = ps.getGeneratedKeys();
            while (resultSet.next())
            {
                phoneID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
                resultSet.close();
        }


        return phoneID;
    }
}
