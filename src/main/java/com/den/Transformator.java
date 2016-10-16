package com.den;


import com.den.db.Connector;
import com.den.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Transformator {

    public List<Integer> getIdResultList() {

        List<Integer> listOfId = new ArrayList<Integer>();
        try {
            Connector connector = new Connector();
            connector.connect();
            ResultSet rs = connector.getResultSet("Select id from crawler");
            while (rs.next()) {
                listOfId.add(rs.getInt("id"));
            }
            connector.closeConnection();
        } catch (SQLException e) {
        }
        return listOfId;
    }

    public List<Result> convertToResultList() {
          List<Integer> list = getIdResultList();
        List<Result> resultList = new ArrayList<Result>();
        Iterator iterator = list.iterator();
        Connector connector = new Connector();
        connector.connect();
        ObjectMapper mapper = new ObjectMapper();
        String query = "Select result from crawler where id = ";
        Result res;
        ResultSet rs;
        try {
            while (iterator.hasNext()) {
                rs = connector.getResultSet(query + iterator.next());
                res = mapper.readValue(rs.getString("result"), Result.class);
                resultList.add(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void showHowItCanBe() {
        List<Result> list = convertToResultList();
        for (int i = 0; i <list.get(0).getResponse().size(); i++) {
            System.out.println(list.get(0).getResponse().get(i).getId()+ "--"
                    + list.get(0).getResponse().get(i).getFirst_name() + "--"
                    + list.get(0).getResponse().get(i).getLast_name() + "--"
                    + list.get(0).getResponse().get(i).getCompany());
        }
    }


    public static void main(String[] args) throws SQLException, IOException {
        Transformator transformator = new Transformator();
        System.out.println(transformator.getIdResultList());
        System.out.println(transformator.convertToResultList());
        transformator.showHowItCanBe();

    }
}
