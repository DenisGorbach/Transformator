package com.den.dao.impl;

import com.den.dao.VkUniversityDao;
import com.den.dao.VkUserDao;
import com.den.model.UniversityInformation;
import com.den.model.VkUniversity;
import com.den.model.VkUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


public class VkUniversityDaoImpl extends AbstractDao<VkUniversity> implements VkUniversityDao{

    public VkUniversityDaoImpl() {
    }

    public ArrayList<Integer> getListOfIdOfUniversities() {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.property("idUniversity"));
        ArrayList<Integer> list = (ArrayList<Integer>) criteria.list();
        getSession().close();
        return list;
    }

    public VkUniversity getByVkId(int id) {
        Session s = getSession();
        s.beginTransaction();
        Criteria criteria = s.createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("idUniversity", id));
        VkUniversity vkUniversity = (VkUniversity) criteria.uniqueResult();
        s.close();
        return vkUniversity;
    }
}
