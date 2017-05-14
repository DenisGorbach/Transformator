package com.den.util;

import com.den.dao.UniversityInformationDao;
import com.den.dao.VkUniversityDao;
import com.den.dao.VkUserDao;
import com.den.dao.impl.UniversityInformationDaoImpl;
import com.den.dao.impl.VkUniversityDaoImpl;
import com.den.dao.impl.VkUserDaoImpl;
import com.den.model.UniversityInformation;
import com.den.model.VkUniversity;
import com.den.model.VkUser;


public class Main {
    public static void main(String[] args) {
        VkUserDao vkUserDao = new VkUserDaoImpl();
        VkUniversityDao vkUniversityDao = new VkUniversityDaoImpl();
        UniversityInformationDao universityInformationDao = new UniversityInformationDaoImpl();

        VkUniversity university = new VkUniversity();
        university.setIdUniversity(1111);
        university.setIdUniversityCity(2222);
        university.setNameUniversity("ONPU");
        vkUniversityDao.add(university);

        VkUser vkUser = new VkUser();
        vkUser.setUid(11111);
        vkUser.setCountry(1);
        vkUser.setCity(1);
        vkUser.setBDate("123");
        vkUser.setFirst_name("First name");
        vkUser.setLast_name("Last name");
        vkUserDao.add(vkUser);

        UniversityInformation universityInformation = new UniversityInformation();
        universityInformation.setUniversity(university);
        universityInformation.setChair(11111);
        universityInformation.setChairName("chair_name");
        universityInformation.setFaculty(1111);
        universityInformation.setFacultyName("Name");
        universityInformation.setGraduation(2020);
        universityInformation.setVkUser(vkUser);
        universityInformationDao.add(universityInformation);

        System.out.println(vkUserDao.getById(2).toString());
        System.exit(0);
    }
}
