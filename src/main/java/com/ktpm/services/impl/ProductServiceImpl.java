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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ad
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (id, product_name, price, origin, product_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getProductName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getOrigin());
            pstmt.setString(5, product.getProductType().toString());
            pstmt.executeUpdate();
        }
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
            pstmt.setString(5, product.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(String id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Product getProductById(String id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                product = new Product(rs.getString("id"),
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
            while (rs.next()){
            Product  product = new Product(rs.getString("id"),
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
