package com.example.s22comp1011w2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.ArrayList;

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

    /**
     * This method will return a list of all the phones in the DB
     */
    public static ArrayList<Phone> getPhonesFromDB()
    {
        ArrayList<Phone> phones = new ArrayList<>();

        String sql = "SELECT phones.phoneID, make, model, os, ram, backCameraMP, price, batteryLifeInHours,quantityInStock,COUNT(customerID) AS unitsSold " +
                "FROM phones LEFT JOIN sales ON phones.phoneID = sales.phoneID " +
                "GROUP BY phones.phoneID;";

        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next())
            {
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                String os = resultSet.getString("os");
                int ram = resultSet.getInt("ram");
                int backCameraMP = resultSet.getInt("backCameraMP");
                double price = resultSet.getDouble("price");
                int batteryLifeInHours = resultSet.getInt("batteryLifeInHours");
                int quantityInStock = resultSet.getInt("quantityInStock");
                int phoneID = resultSet.getInt("phoneID");
                int unitsSold = resultSet.getInt("unitsSold");

                phones.add(new Phone(make,model,os,ram,backCameraMP,price,batteryLifeInHours,quantityInStock,phoneID,unitsSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phones;
    }

    public static XYChart.Series<String, Integer> getPhonesSoldPerMonth()
    {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Units Sold");

        String sql = "SELECT MONTHNAME(dateSold) AS month, COUNT(customerID) AS unitsSold "+
                    "FROM SALES " +
                    "GROUP BY MONTHNAME(dateSold) " +
                    "ORDER BY MONTH(dateSold);";

        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next())
            {
                String month = resultSet.getString("month");
                int unitsSold = resultSet.getInt("unitsSold");

                series.getData().add(new XYChart.Data<>(month,unitsSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    public static ObservableList<PieChart.Data> pieChartSales()
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        String sql = "SELECT model AS phone,COUNT(customerID) AS unitsSold " +
                    "FROM phones LEFT JOIN sales ON phones.phoneID = sales.phoneID " +
                    "GROUP BY phones.phoneID;";

        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next())
            {
                String phone = resultSet.getString("phone");
                int unitsSold = resultSet.getInt("unitsSold");

                data.addAll(new PieChart.Data(phone, unitsSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;

    }
}
