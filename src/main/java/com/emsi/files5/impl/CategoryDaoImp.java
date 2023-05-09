package com.emsi.files5.impl;

import com.emsi.files5.IDao.CategoryDao;
import com.emsi.files5.entities.Author;
import com.emsi.files5.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImp implements CategoryDao {
    private Connection conn= DB.getConnection();

    @Override
    public Category findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM category WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                category.setDescription(rs.getString("Description"));
                return category;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find the Category");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public List<Category> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM category");
            rs = ps.executeQuery();
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category() ;
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                category.setDescription(rs.getString("Description"));
                list.add(category);
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
    public void insert(Category category) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "INSERT INTO category (`name`, `Description`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    category.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("no row was effected");;
            }
        } catch (SQLException e) {
            System.err.println("Cant inset Category");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Category findByName(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM category WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setName(rs.getString("Name"));
                category.setDescription(rs.getString("Description"));
                return category;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find the Category");;
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
            ps = conn.prepareStatement("DELETE FROM category");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Cant delete categories");;
        } finally {
            DB.closeStatement(ps);
        }
    }
}

