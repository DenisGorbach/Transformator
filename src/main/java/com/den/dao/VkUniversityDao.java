package com.den.dao;

import com.den.model.VkUniversity;

import java.util.ArrayList;

public interface VkUniversityDao extends ModelDao<VkUniversity>{

    ArrayList<Integer> getListOfIdOfUniversities();

    VkUniversity getByVkId(int id);
}
