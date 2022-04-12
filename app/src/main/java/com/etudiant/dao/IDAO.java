package com.etudiant.dao;

import java.util.List;

public interface IDAO<T> {
    public List<T> findAll();
    public T getById(Integer id);
    public void deleteById(Integer id);
    public long save(T t);
    public void save(T t, Integer id);
}
