/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Product;
import com.ktpm.services.ProductService;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ad
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public int addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products ( product_name, price, origin, product_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getOrigin());
            pstmt.setString(4, product.getProductType().toString());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    @Override
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET product_name = ?, price = ?, origin = ?, product_type = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getOrigin());
            pstmt.setString(4, product.getProductType().toString());
            pstmt.setInt(5, product.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("origin"),
                        Product.ProductType.valueOf(rs.getString("product_type")));
            }
        }
        return product;
    }

    @Override
    public Product getProductByName(String productName) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_name = ?";
        Product product = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("origin"),
                        Product.ProductType.valueOf(rs.getString("product_type")));
            }
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("origin"),
                        Product.ProductType.valueOf(rs.getString("product_type")));
                products.add(product);
            }
        }
        return products;
    }
}
