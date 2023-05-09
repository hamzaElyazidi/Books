package com.emsi.files5.impl;

import com.emsi.files5.IDao.AuthorDao;
import com.emsi.files5.IDao.CategoryDao;
import com.emsi.files5.entities.Author;
import com.emsi.files5.entities.Book;
import com.emsi.files5.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDaoImp implements AuthorDao {
    private Connection conn= DB.getConnection();

    @Override
    public Author findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM author WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("Id"));
                author.setName(rs.getString("Name"));
                author.setAboutTheAuthor(rs.getString("AboutTheAuthor"));
                return author;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find the author");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public List<Author> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM author");
            rs = ps.executeQuery();
            List<Author> list = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author() ;
                author.setId(rs.getInt("Id"));
                author.setName(rs.getString("Name"));
                author.setAboutTheAuthor(rs.getString("AboutTheAuthor"));
                list.add(author);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Cannot select the Author");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void insert(Author author) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "INSERT INTO author (`name`, `AboutTheAuthor`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getName());
            ps.setString(2, author.getAboutTheAuthor());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    author.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("no row was effected");;
            }
        } catch (SQLException e) {
            System.err.println("Cant inset author");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Author findByName(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM author WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("Id"));
                author.setName(rs.getString("Name"));
                author.setAboutTheAuthor(rs.getString("AboutTheAuthor"));
                return author;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find the author");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteAll() {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM author");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cant delete books");;
        } finally {
            DB.closeStatement(ps);
        }
    }

}
