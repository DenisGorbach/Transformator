package com.den.dao.impl;

import com.den.dao.VkCareerDao;
import com.den.dao.VkUniversityDao;
import com.den.model.VkCareer;
import com.den.model.VkUniversity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;


public class VkCareerDaoImpl extends AbstractDao<VkCareer> implements VkCareerDao {

    public VkCareerDaoImpl() {
    }

    @Override
    public VkCareer getByVkId(int id) {
        return null;
    }

//    public ArrayList<Integer> getListOfIdOfUniversities() {
//        Criteria criteria = getSession().createCriteria(clazz);
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        criteria.setProjection(Projections.property("idUniversity"));
//        ArrayList<Integer> list = (ArrayList<Integer>) criteria.list();
//        getSession().close();
//        return list;
//    }

//    public VkCareer getByVkId(int id) {
//        Session s = getSession();
//        s.beginTransaction();
//        Criteria criteria = s.createCriteria(clazz);
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        criteria.add(Restrictions.eq("idUniversity", id));
//        VkUniversity vkUniversity = (VkUniversity) criteria.uniqueResult();
//        s.close();
//        return vkUniversity;
//    }
}
