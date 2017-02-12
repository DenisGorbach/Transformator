package com.den.dao;

import com.den.model.Model;

import java.util.List;

public interface ModelDao<T extends Model> {

    List<T> getAll();

    T getById(int id);

    void add(T model);

    void update(T model);

    void remove(T model);

}