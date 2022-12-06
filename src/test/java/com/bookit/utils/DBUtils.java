package com.bookit.utils;

import org.openqa.selenium.WebDriver;

import java.sql.*;

public class DBUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public static void dbConnect(){
        String url = ConfigurationReader.get("DBUrl");
        String username = ConfigurationReader.get("DBUsername");
        String password = ConfigurationReader.get("DBPassword");
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(":) CONNECTION SUCCESSFUL");
        } catch (SQLException e) {
            System.out.println("!!! CONNECTING TO DATABASE FAILED !!!");
        }
    }

    public static ResultSet executeQuery(String query){
        dbConnect();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("EXECUTE QUERY FAILED");
        }

        return resultSet;
    }
}
