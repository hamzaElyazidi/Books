package com.emsi.files5.service;

import com.emsi.files5.IDao.CategoryDao;
import com.emsi.files5.entities.Category;
import com.emsi.files5.impl.CategoryDaoImp;

import java.util.List;

public class CategoryService {
    private  CategoryDao categoryDao = new CategoryDaoImp() ;
    public Category findById(Integer id) {return  categoryDao.findById(id) ;}
    public List<Category> findAll() { return categoryDao.findAll() ;}

    public  void insert(Category category) {categoryDao.insert(category) ;}

    public Category findByName(String name) { return categoryDao.findByName(name) ;}
    public void deleteAll() {categoryDao.deleteAll() ;};
}
