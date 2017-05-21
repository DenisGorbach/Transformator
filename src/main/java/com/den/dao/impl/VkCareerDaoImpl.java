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
    public ArrayList<String> getListOfGroupsName() {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.property("companyName"));
        ArrayList<String> list = (ArrayList<String>) criteria.list();
        getSession().close();
        return list;
    }

    @Override
    public VkCareer getByGroupId(int id) {
        Session s = getSession();
        s.beginTransaction();
        Criteria criteria = s.createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("groupId", id));
        VkCareer vkCareer = (VkCareer) criteria.uniqueResult();
        s.close();
        return vkCareer;
    }

    @Override
    public VkCareer getByCompanyName(String name) {
        Session s = getSession();
        s.beginTransaction();
        Criteria criteria = s.createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("companyName", name));
        VkCareer vkCareer = (VkCareer) criteria.uniqueResult();
        s.close();
        return vkCareer;
    }

    @Override
    public ArrayList<Integer> getListOfGroupsId() {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.property("groupId"));
        ArrayList<Integer> list = (ArrayList<Integer>) criteria.list();
        getSession().close();
        return list;
    }

}
