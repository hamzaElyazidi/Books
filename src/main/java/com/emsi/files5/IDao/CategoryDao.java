package com.emsi.files5.IDao;

import com.emsi.files5.entities.Category;

import java.util.List;

public interface CategoryDao {
    Category findById(Integer id) ;
    List<Category> findAll() ;


    void insert(Category category);

    Category findByName(String name);

    void deleteAll();
}
