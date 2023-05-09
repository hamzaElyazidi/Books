package com.emsi.files5.IDao;

import com.emsi.files5.entities.Author;

import java.util.List;

public interface AuthorDao {
    Author findById(Integer id) ;
    List<Author> findAll() ;
    void insert(Author author) ;
    Author findByName(String name) ;
    void deleteAll() ;
}
