package com.den.db;


import java.sql.*;

public class Connector {

    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:crawler.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
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
            Connector connector = new Connector();
            connector.connect();

            connector.executeQuery("insert into crawler values(null, '{\n" +
                    "\"group_id\": 2,\n" +
                    "\"social_network\": \"vk\",\n" +
                    "\"response\":[{\n" +
                    "\"id\":210700286,\n" +
                    "\"first_name\":\"Lindsey\",\n" +
                    "\"last_name\":\"Stirling\",\n" +
                    "\"company\":\"DoIt\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"id\":210700286,\n" +
                    "\"first_name\":\"Lindsey\",\n" +
                    "\"last_name\":\"Stirling\",\n" +
                    "\"company\":\"Sigma\"\n" +
                    "}\n" +
                    "]\n" +
                    "}')");
            ResultSet rs = connector.getResultSet("select * from crawler");
        try {
            while(rs.next())
            {
                // read the result set
                System.out.println("json = " + rs.getString("result"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            connector.closeConnection();
    }
}
