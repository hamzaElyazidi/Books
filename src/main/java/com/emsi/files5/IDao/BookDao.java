package com.emsi.files5.IDao;

import com.emsi.files5.entities.Book;

import java.util.List;

public interface BookDao {
    void insert(Book book) ;
    void update (Book book) ;
    void deleteById (Integer id) ;
    Book findById (Integer id) ;
    List<Book> findAll() ;
    void deleteAll() ;

}
