package com.den.dao;

import com.den.model.VkCareer;
import com.den.model.VkUniversity;

import java.util.ArrayList;

public interface VkCareerDao extends ModelDao<VkCareer>{

//    ArrayList<Integer> getListOfIdOfUniversities();

    VkCareer getByVkId(int id);
}
