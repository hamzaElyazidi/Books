package com.emsi.files5.impl;

import com.emsi.files5.IDao.AuthorDao;
import com.emsi.files5.IDao.BookDao;
import com.emsi.files5.IDao.CategoryDao;
import com.emsi.files5.entities.Author;
import com.emsi.files5.entities.Book;
import com.emsi.files5.entities.Category;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class BookDaoImp implements BookDao {
    private Connection conn= DB.getConnection();
    private AuthorDao authorDao = new AuthorDaoImp() ;
    private CategoryDao categoryDao = new CategoryDaoImp() ;



    @Override
    public void insert(Book book) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "INSERT INTO book (`name`, `numberOfPages`, `isbn`, `price`, `publishedAt`, `AuthorId`, `CategoryId`) VALUES (?, ?, ?, ? , ? , ? , ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getName());
            ps.setInt(2, book.getNumberOfPages());
            ps.setString(3, book.getIsbn());
            ps.setFloat(4, book.getPrice());
            ps.setDate(5, new java.sql.Date(book.getPublishedAt().getTime()));
            ps.setInt(6, book.getAuthor() != null ? book.getAuthor().getId() : Types.NULL );
            ps.setInt(7, book.getCategory() != null ? book.getCategory().getId() : Types.NULL);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    book.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("no row was effected");;
            }
        } catch (SQLException e) {
            System.err.println("Cant inset book");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Book book) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE book SET name = ? , numberOfPages = ? , isbn = ? , price = ? , publishedAt = ? , AuthorId = ? , CategoryId = ? WHERE Id = ?");
            ps.setString(1, book.getName());
            ps.setInt(2, book.getNumberOfPages());
            ps.setString(3, book.getIsbn());
            ps.setFloat(4, book.getPrice());
            ps.setDate(5, new java.sql.Date(book.getPublishedAt().getTime()));
            ps.setInt(6, book.getAuthor().getId());
            ps.setInt(7, book.getCategory().getId());
            ps.setInt(8, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cant update book");;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM book WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cant delete book");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Book findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Author author = authorDao.findById(rs.getInt("AuthorId")) ;
                Category category = categoryDao.findById(rs.getInt("CategoryId")) ;
                Book book = instantiateBook(rs,author,category) ;
                return book;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find book by id");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Book> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM book");
            rs = ps.executeQuery();
            List<Book> list = new ArrayList<>();
            Map<Integer, Author> mapAuthor = new HashMap<>();
            Map<Integer, Category> mapCategory = new HashMap<>();
            while (rs.next()) {
                Author author = mapAuthor.get(rs.getInt("AuthorId"));
                Category category  = mapCategory.get(rs.getInt("CategoryId"));
                //System.out.println(dep);
                if (author == null) {
                    author = authorDao.findById(rs.getInt("AuthorId"));
                    mapAuthor.put(rs.getInt("AuthorId"), author);
                }
                if (category == null) {
                    category = categoryDao.findById(rs.getInt("CategoryId")) ;
                    mapCategory.put(rs.getInt("CategoryId"), category);
                }
                Book book = instantiateBook(rs, author , category);
                list.add(book);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("Cannot select the books");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void deleteAll() {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM book");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cant delete books");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    private Book instantiateBook(ResultSet rs, Author author, Category category) throws SQLException {
     Book book = new Book() ;
     book.setId(rs.getInt("Id"));
     book.setName(rs.getString("Name"));
     book.setIsbn(rs.getString("Isbn"));
     book.setNumberOfPages(rs.getInt("NumberOfPages"));
     book.setPrice(rs.getFloat("Price"));
     book.setPublishedAt(new Date(rs.getTimestamp("publishedAt").getTime()));
     book.setAuthor(author);
     book.setCategory(category);
     return book ;
    }

//    private Category instantiateCategory(ResultSet rs) throws SQLException{
//      Category category = new Category() ;
//      category.setId(rs.getInt("CategoryId"));
//      return category ;
//    }
//
//    private Author instantiateAuthor(ResultSet rs) throws SQLException {
//        Author author = new Author() ;
//        Author author1 = authorDao.findById(rs.getInt("AuthorId")) ;
//        author.setId(rs.getInt("AuthorId"));
//        return author ;
//    }
}
