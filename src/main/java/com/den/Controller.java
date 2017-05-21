package com.den;


import com.den.dao.*;
import com.den.dao.impl.*;
import com.den.db.HiveConnector;
import com.den.db.PostgresConnector;
import com.den.model.*;
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
    ObjectMapper mapper;

    private List<Integer> getIdResultList() {

        List<Integer> listOfId = new ArrayList<Integer>();
        try {
            PostgresConnector connector = new PostgresConnector();
            connector.connect();
            ResultSet rs = connector.getResultSet("Select id from \"unsort\";");
            while (rs.next()) {
                listOfId.add(rs.getInt("id"));
            }
            connector.closeConnection();
        } catch (SQLException ignored) {}
        return listOfId;
    }


    private void convert() throws SQLException, IOException {
        PostgresConnector postgresConnector = new PostgresConnector();
        postgresConnector.connect();

        //DAO
        VkUserDao vkUserDao = new VkUserDaoImpl();
        VkUniversityDao vkUniversityDao = new VkUniversityDaoImpl();
        UniversityInformationDao universityInformationDao = new UniversityInformationDaoImpl();
        VkCareerDao vkCareerDao = new VkCareerDaoImpl();
        CareerInformationDao careerInformationDao = new CareerInformationDaoImpl();
        ArrayList<Integer> universityIdList = vkUniversityDao.getListOfIdOfUniversities();
        ArrayList<String> careersNames = vkCareerDao.getListOfGroupsName();
        ArrayList<Integer> careersGroupIds = vkCareerDao.getListOfGroupsId();
//        hiveConnector = new HiveConnector();
//        hiveConnector.connect();
//        hiveConnector.executeQuery("CREATE TABLE IF NOT EXISTS vkuser (uid int, b_date String, city String, country String, first_name String, last_name String)");

        mapper = new ObjectMapper();
        List<Integer> listOfId = getIdResultList();

        for (Integer aListOfId : listOfId) {
//        String query = "Select \"response\" from \"unsort\" where id = 1";
            String query = "Select \"response\" from \"unsort\" where id = " + aListOfId;
        ResultSet rs = postgresConnector.getResultSet(query);
        ObjectNode jsonObject = null;
        while (rs.next()) {
            jsonObject = mapper.readValue(rs.getString("response"), ObjectNode.class);
        }
        //there is an array of unsorted users
        ArrayNode arrayNode = mapper.readValue(String.valueOf(jsonObject.get("response").get(0).get("response")), ArrayNode.class);

        for (int n = 0; n < 1; n++) {
            ArrayNode currentArrayNode = mapper.readValue(String.valueOf(arrayNode.get(n)), ArrayNode.class);
            currentArrayNode.remove(0);
            jsonObject.set("response", currentArrayNode.get(n));



            ArrayList<VkUser> vkUsers = new ArrayList<>();
            VkUser vkUser;
            UniversityInformation universityInformation;
            VkUniversity vkUniversity;
            ArrayNode universities;

            VkCareer career;
            CareerInformation careerInformation;
            ArrayNode careers;

            for (int i = 0; i < currentArrayNode.size(); i++) {
                vkUser = setBasicFieldsForVkUser(currentArrayNode.get(i));
                vkUserDao.add(vkUser);

                universities = mapper.readValue(String.valueOf(currentArrayNode.get(i).get("universities")), ArrayNode.class);
                careers = mapper.readValue(String.valueOf(currentArrayNode.get(i).get("career")), ArrayNode.class);

                if (universities != null) {
                    if (universities.size() != 0) {
                        for (int j = 0; j < universities.size(); j++) {
                            int vkUniversityId = universities.get(j).get("id").asInt();
                            if (universityIdList.contains(vkUniversityId)) {
                                vkUniversity = vkUniversityDao.getByVkId(vkUniversityId);
                            } else {
                                vkUniversity = setFieldsForUniversity(universities.get(j));
                                vkUniversityDao.add(vkUniversity);
                                universityIdList.add(vkUniversityId);
                            }

                            universityInformation = setFieldsForUniversityInformation(universities.get(j));
                            universityInformation.setUniversity(vkUniversity);
                            universityInformation.setVkUser(vkUser);
                            universityInformationDao.add(universityInformation);
                        }
                    }
                }

                if (careers != null) {
                    if (careers.size() != 0) {
                        for (int j = 0; j < careers.size(); j++) {
                            career = setFieldsForCareer(careers.get(j));
                            System.out.println(career);
                            if (career.getCompanyName() != null) {
                                if (careersNames.contains(career.getCompanyName())) {
                                    career = vkCareerDao.getByCompanyName(career.getCompanyName());
                                } else {
                                    vkCareerDao.add(career);
                                    careersNames.add(career.getCompanyName());
                                }
                            }

                            if (career.getGroupId() != null) {
                                if (careersGroupIds.contains(career.getGroupId())) {
                                    career = vkCareerDao.getByGroupId(career.getGroupId());
                                } else {
                                    vkCareerDao.add(career);
                                    careersGroupIds.add(career.getGroupId());
                                }
                            }

                            careerInformation = setFieldsForCareerInformation(careers.get(j));
                            careerInformation.setCareer(career);
                            careerInformation.setUser(vkUser);
                            careerInformationDao.add(careerInformation);
                        }
                    }
                }

                vkUsers.add(vkUser);
                System.out.println("Added user");
            }
        }

        }
        postgresConnector.closeConnection();
    }

    private void insertIntoHive(List<VkUser> userList) {
        String insertQuery = "Insert into vkuser values ";
        String user;
        String b_date;
        String firstName;
        String lastName;
        for (VkUser vkUser : userList) {
            if (vkUser.getBDate() != null) {
                b_date = "'" + vkUser.getBDate() + "'";
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

    private VkUser setBasicFieldsForVkUser(JsonNode userObjectFromJson) {
        VkUser vkUser = new VkUser();
        vkUser.setUid(userObjectFromJson.get("uid").asInt());
        try {
            vkUser.setBDate(userObjectFromJson.get("bdate").asText());
        } catch (NullPointerException e) {
            vkUser.setBDate(null);
        }
        vkUser.setFirst_name(userObjectFromJson.get("first_name").asText());
        vkUser.setLast_name(userObjectFromJson.get("last_name").asText());
        vkUser.setCity(userObjectFromJson.get("city").asInt());
        vkUser.setCountry(userObjectFromJson.get("country").asInt());
        return vkUser;
    }

    private UniversityInformation setFieldsForUniversityInformation(JsonNode universityObjectFromJson) {
        UniversityInformation universityInformation = new UniversityInformation();
        try {
            universityInformation.setFacultyName(universityObjectFromJson.get("faculty_name").asText());
        } catch (NullPointerException e) {
            universityInformation.setFacultyName(null);
        }
        try {
            universityInformation.setFaculty(universityObjectFromJson.get("faculty").asInt());
        } catch (NullPointerException e) {
            universityInformation.setFaculty(null);
        }
        try {
            universityInformation.setChairName(universityObjectFromJson.get("chair_name").asText());
        } catch (NullPointerException e) {
            universityInformation.setChairName(null);
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

    private VkUniversity setFieldsForUniversity(JsonNode universityObjectFromJson) {
        VkUniversity vkUniversity = new VkUniversity();
        vkUniversity.setIdUniversity(universityObjectFromJson.get("id").asInt());
        vkUniversity.setIdUniversityCity(universityObjectFromJson.get("city").asInt());
        vkUniversity.setNameUniversity(universityObjectFromJson.get("name").asText());
        return vkUniversity;
    }

    private VkCareer setFieldsForCareer(JsonNode careerObjectFromJson){
        VkCareer career = new VkCareer();

        try {
            career.setCompanyName(careerObjectFromJson.get("company").asText());
        } catch (NullPointerException e) {
            career.setCompanyName(null);
        }

        try {
            career.setGroupId(careerObjectFromJson.get("group_id").asInt());
        } catch (NullPointerException e) {
            career.setGroupId(null);
        }
        return career;
    }

    private CareerInformation setFieldsForCareerInformation(JsonNode careerInformationObjectFromJson){
        CareerInformation careerInformation = new CareerInformation();
        try {
            careerInformation.setCity(careerInformationObjectFromJson.get("city_id").asInt());
        } catch (NullPointerException e) {
            careerInformation.setCity(null);
        }

        try {
            careerInformation.setCountry(careerInformationObjectFromJson.get("country_id").asInt());
        } catch (NullPointerException e) {
            careerInformation.setCountry(null);
        }

        try {
            careerInformation.setFrom(careerInformationObjectFromJson.get("from").asInt());
        } catch (NullPointerException e) {
            careerInformation.setFrom(null);
        }

        try {
            careerInformation.setUntil(careerInformationObjectFromJson.get("until").asInt());
        } catch (NullPointerException e) {
            careerInformation.setUntil(null);
        }

        try {
            careerInformation.setPosition(careerInformationObjectFromJson.get("position").asText());
        } catch (NullPointerException e) {
            careerInformation.setPosition(null);
        }

        return careerInformation;
    }

    public static void main(String[] args) throws SQLException, IOException {
        Controller transformator = new Controller();
        transformator.convert();
//        CareerInformation careerInformation = new CareerInformation();
//        careerInformation.setFrom(12);
//        CareerInformationDaoImpl careerInformationDao = new CareerInformationDaoImpl();
//        careerInformationDao.add(careerInformation);
        System.exit(0);
    }
}