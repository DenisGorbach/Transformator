package com.den.db;

import java.sql.*;

/**
 * Created by den on 11.12.16.
 */
public class HiveConnector {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;

    public void connect(){
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection("jdbc:hive2://91.196.94.24:10000/default", "", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement(){
        return this.statement;
    }

    public ResultSet getResultSet(String query){
        try {
            resultSet = getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void executeQuery(String query){
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if (connection!=null) try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HiveConnector connector = new HiveConnector();
        connector.connect();

        String query = "INSERT INTO TABLE employee VALUES (4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')," +
                "(4, 'name', 'salary', 'destination')";

        String createQuery = "CREATE TABLE IF NOT EXISTS vkuser (uid int, b_date String, city String, country String, first_name String, last_name String)";

        connector.executeQuery(query);
        ResultSet resultSet = connector.getResultSet("Select * from employee");
        System.out.println("Result: ");
        try {
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getString(1) + "; Name: " + resultSet.getString(2) +
                        "; salary: " + resultSet.getString(3) + "; destination: " + resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.closeConnection();
    }
}
