package com.den.db;

import java.sql.*;

public class MysqlConnector {

    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;

    public void connect(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/crawler", "postgres", "root");
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

    public static void main(String[] args) throws ClassNotFoundException
    {
        PostgresConnector connector = new PostgresConnector();
        connector.connect();

//        String query = "Insert into \"University\"(vkId, name, city) values(123123, 'ONPU', 'Odessa');";
//        connector.executeQuery(query);
        String query = "Select * from \"Unsorted\";";
        ResultSet resultSet = connector.getResultSet(query);
        try {
            while (resultSet.next()){
                System.out.println(resultSet.getString("response"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        connector.closeConnection();
    }
}
