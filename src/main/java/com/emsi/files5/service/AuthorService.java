package com.emsi.files5.service;

import com.emsi.files5.IDao.AuthorDao;
import com.emsi.files5.entities.Author;
import com.emsi.files5.impl.AuthorDaoImp;

import java.util.List;

public class AuthorService {
    private  AuthorDao authorDao = new AuthorDaoImp() ;
    public Author findById(Integer id) {return authorDao.findById(id) ; }
    public List<Author> findAll() {return  authorDao.findAll() ;}
    public  void insert(Author author){ authorDao.insert(author);}
    public Author findByName(String name) {return authorDao.findByName(name) ;}
    public void deleteAll() {authorDao.deleteAll();}
}
