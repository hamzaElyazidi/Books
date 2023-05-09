package com.emsi.files5.service;

import com.emsi.files5.IDao.BookDao;
import com.emsi.files5.entities.Book;
import com.emsi.files5.impl.BookDaoImp;

import java.util.List;

public class BookService {
    private  BookDao bookDao = new BookDaoImp() ;
    private AuthorService authorService ;
    private CategoryService categoryService ;


    public List<Book> findAll() {return bookDao.findAll() ;}
    public  void insert(Book book) {bookDao.insert(book);}
    public void remove (Integer id) {bookDao.deleteById(id);}

    public void update (Book book) {bookDao.update(book);}
    public Book findById (Integer id) {return bookDao.findById(id) ;}
    public void deleteAll() {bookDao.deleteAll();}
}
