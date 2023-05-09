package com.emsi.files5.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name ;
    private int numberOfPages;
    private  String isbn ;
    private  float price  ;
    private Date publishedAt ;
    private Author  author ;
    private  Category category ;


    public Book(Integer id, String name, int numberOfPages, String isbn, float price, Date publishedAt, Author author,
                Category category) {
        this.id = id;
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.price = price;
        this.publishedAt = publishedAt;
        this.author = author;
        this.category = category;
    }

    public Book(String name, int numberOfPages, String isbn, float price, Date publishedAt, Author author, Category category) {
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.price = price;
        this.publishedAt = publishedAt;
        this.author = author;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public Date getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  "Book : " + this.getId() + "\n" +
                "   name : " + this.getName()  + "\n" +
                "   isbn : " + this.getIsbn() + "\n" +
                "   number of pages : " + this.getNumberOfPages() + "\n" +
                "   price : " + this.getPrice() + " $ \n" +
                "   publication date  : " + this.getPublishedAt() + "\n" +
                "   Author : " + (getAuthor() !=null ? this.getAuthor().getId():"null")+ "\n" +
                "       name  : " + (getAuthor() !=null ? this.getAuthor().getName():"") + "\n" +
                "       about the author : " + (getAuthor() !=null ? this.getAuthor().getAboutTheAuthor():"") + "\n" +
                "   Category : " + (getCategory() !=null ? this.getCategory().getId():"null")+ "\n" +
                "       name  : " + (getCategory() !=null ? this.getCategory().getName():"") + "\n" +
                "       description  : " + (getCategory() !=null ? this.getCategory().getDescription():"")  + "\n";
    }

}
