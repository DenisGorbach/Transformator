package com.den;


import com.den.dao.UniversityInformationDao;
import com.den.dao.VkUniversityDao;
import com.den.dao.VkUserDao;
import com.den.dao.impl.UniversityInformationDaoImpl;
import com.den.dao.impl.VkUniversityDaoImpl;
import com.den.dao.impl.VkUserDaoImpl;
import com.den.db.HiveConnector;
import com.den.db.PostgresConnector;
import com.den.model.UniversityInformation;
import com.den.model.VkJob;
import com.den.model.VkUniversity;
import com.den.model.VkUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Controller {

    private HiveConnector hiveConnector;

    public List<Integer> getIdResultList() {

        List<Integer> listOfId = new ArrayList<Integer>();
        try {
            PostgresConnector connector = new PostgresConnector();
            connector.connect();
            ResultSet rs = connector.getResultSet("Select id from \"Unsorted\";");
            while (rs.next()) {
                listOfId.add(rs.getInt("id"));
            }
            connector.closeConnection();
        } catch (SQLException e) {
        }
        return listOfId;
    }


    public void convert() throws SQLException, IOException {
        PostgresConnector postgresConnector = new PostgresConnector();
        postgresConnector.connect();

        hiveConnector = new HiveConnector();
        hiveConnector.connect();
        hiveConnector.executeQuery("CREATE TABLE IF NOT EXISTS vkuser (uid int, b_date String, city String, country String, first_name String, last_name String)");

        ObjectMapper mapper = new ObjectMapper();
        List<Integer> listOfId = getIdResultList();

//        for (Integer aListOfId : listOfId) {
        String query = "Select \"response\" from \"Unsorted\" where id = 1";
//            String query = "Select \"response\" from \"Unsorted\" where id = " + aListOfId;
        ResultSet rs = postgresConnector.getResultSet(query);
        ObjectNode jsonObject = null;
        while (rs.next()) {
            jsonObject = mapper.readValue(rs.getString("response"), ObjectNode.class);
        }
        //there is an array of unsorted users
        ArrayNode arrayNode = mapper.readValue(String.valueOf(jsonObject.get("response").get(0).get("response")), ArrayNode.class);

//            for (int n = 0; n < arrayNode.size(); n++) {
        for (int n = 0; n < 1; n++) {
            ArrayNode currentArrayNode = mapper.readValue(String.valueOf(arrayNode.get(n)), ArrayNode.class);
            currentArrayNode.remove(0);
            jsonObject.set("response", currentArrayNode.get(n));

            //DAO
            VkUserDao vkUserDao = new VkUserDaoImpl();
            VkUniversityDao vkUniversityDao = new VkUniversityDaoImpl();
            UniversityInformationDao universityInformationDao = new UniversityInformationDaoImpl();

            ArrayList<Integer> universityIdList = vkUniversityDao.getListOfIdOfUniversities();

            ArrayList<VkUser> vkUsers = new ArrayList<VkUser>();
            VkUser vkUser;
            UniversityInformation universityInformation = null;
            VkUniversity vkUniversity = null;
            ArrayNode universities;

//                for (int i = 0; i < currentArrayNode.size(); i++) {
            for (int i = 0; i < currentArrayNode.size(); i++) {
                vkUser = setBasicFieldsForVkUser(currentArrayNode.get(i));
//                    vkUserDao.add(vkUser);
                    /*
                    universities = mapper.readValue(String.valueOf(currentArrayNode.get(i).get("universities")), ArrayNode.class);

                    if (universities != null) {
                        if (universities.size() != 0) {
                            for (int j = 0; j < universities.size(); j++) {
                                int vkUniversityId = universities.get(j).get("id").asInt();
                                if (universityIdList.contains(vkUniversityId)) {
                                    vkUniversity = vkUniversityDao.getByVkId(vkUniversityId);
                                } else {
                                    vkUniversity = setFieldsForUniversity(universities.get(j));
//                                    vkUniversityDao.add(vkUniversity);
                                    universityIdList.add(vkUniversityId);
                                }

                                universityInformation = setFieldsForUniversityInformation(universities.get(j));
                                universityInformation.setUniversity(vkUniversity);
                                universityInformation.setVkUser(vkUser);
//                                universityInformationDao.add(universityInformation);
                            }
                        }
                    }
*/
                vkUsers.add(vkUser);
                System.out.println("Added user");
            }
            System.out.println("Preparing query for hive");
            insertIntoHive(vkUsers);

        }

//        }
        postgresConnector.closeConnection();
    }

    public void insertIntoHive(List<VkUser> userList) {
        String insertQuery = "Insert into vkuser values ";
        String user;
        String b_date;
        String firstName;
        String lastName;
        for (VkUser vkUser : userList) {
            if (vkUser.getB_date() != null) {
                b_date = "'" + vkUser.getB_date() + "'";
            } else b_date = null;

            if (vkUser.getFirst_name() != null) {
                firstName = "'" + vkUser.getFirst_name() + "'";
            } else firstName = null;

            if (vkUser.getLast_name() != null) {
                lastName = "'" + vkUser.getLast_name() + "'";
            } else lastName = null;

            user = "(" + vkUser.getUid() + ", " + b_date + ", " + vkUser.getCity() + ", " +
                    vkUser.getCountry() + "," + firstName + ", " + lastName + "), ";
            insertQuery = insertQuery.concat(user);
        }
        insertQuery = insertQuery.substring(0, insertQuery.length() - 2);
        System.out.println(insertQuery);
        hiveConnector.executeQuery(insertQuery);
    }

    public VkUser setBasicFieldsForVkUser(JsonNode userObjectFromJson) {
        VkUser vkUser = new VkUser();
        vkUser.setUid(userObjectFromJson.get("uid").asInt());
        try {
            vkUser.setB_date(userObjectFromJson.get("bdate").asText());
        } catch (NullPointerException e) {
            vkUser.setB_date(null);
        }
        vkUser.setFirst_name(userObjectFromJson.get("first_name").asText());
        vkUser.setLast_name(userObjectFromJson.get("last_name").asText());
        vkUser.setCity(userObjectFromJson.get("city").asInt());
        vkUser.setCountry(userObjectFromJson.get("country").asInt());
        return vkUser;
    }

    public UniversityInformation setFieldsForUniversityInformation(JsonNode universityObjectFromJson) {
        UniversityInformation universityInformation = new UniversityInformation();
        try {
            universityInformation.setFaculty_name(universityObjectFromJson.get("faculty_name").asText());
        } catch (NullPointerException e) {
            universityInformation.setFaculty_name(null);
        }
        try {
            universityInformation.setFaculty(universityObjectFromJson.get("faculty").asInt());
        } catch (NullPointerException e) {
            universityInformation.setFaculty(null);
        }
        try {
            universityInformation.setChair_name(universityObjectFromJson.get("chair_name").asText());
        } catch (NullPointerException e) {
            universityInformation.setChair_name(null);
        }
        try {
            universityInformation.setChair(universityObjectFromJson.get("chair").asInt());
        } catch (NullPointerException e) {
            universityInformation.setChair(null);
        }
        try {
            universityInformation.setGraduation(universityObjectFromJson.get("graduation").asInt());
        } catch (NullPointerException e) {
            universityInformation.setGraduation(null);
        }
        return universityInformation;
    }

    public VkUniversity setFieldsForUniversity(JsonNode universityObjectFromJson) {
        VkUniversity vkUniversity = new VkUniversity();
        vkUniversity.setIdVkUniversity(universityObjectFromJson.get("id").asInt());
        vkUniversity.setIdUniversityCity(universityObjectFromJson.get("city").asInt());
        vkUniversity.setNameVkUniversity(universityObjectFromJson.get("name").asText());
        return vkUniversity;
    }

    public VkJob setFieldsForJob(JsonNode jobObjectFromJson) {
        VkJob vkJob = new VkJob();
        try {
            vkJob.setIdVkJob(jobObjectFromJson.get("id").asLong());
        } catch (NullPointerException e) {
            vkJob.setIdVkJob(0);
        }
        vkJob.setNameVkJob(jobObjectFromJson.get("name").asText());
        return vkJob;
    }

    public static void main(String[] args) throws SQLException, IOException {
        Controller transformator = new Controller();
        transformator.convert();
        System.exit(0);
    }
}