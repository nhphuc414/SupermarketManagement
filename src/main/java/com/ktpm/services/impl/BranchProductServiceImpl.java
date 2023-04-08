/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.BranchProduct;
import com.ktpm.services.BranchProductService;
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
public class BranchProductServiceImpl implements BranchProductService {

    @Override
    public void addBranchProduct(BranchProduct branchProduct) throws SQLException {
        String sql = "INSERT INTO branch_products (id, quantity, product_id, branch_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branchProduct.getId());
            pstmt.setDouble(2, branchProduct.getQuantity());
            pstmt.setString(3, branchProduct.getProductId());
            pstmt.setString(4, branchProduct.getBranchId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateBranchProduct(BranchProduct branchProduct) throws SQLException {
        String sql = "UPDATE branch_products SET quantity=?, product_id=?, branch_id=? WHERE id=?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, branchProduct.getQuantity());
            pstmt.setString(2, branchProduct.getProductId());
            pstmt.setString(3, branchProduct.getBranchId());
            pstmt.setString(4, branchProduct.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBranchProduct(String id) throws SQLException {
        String sql = "DELETE FROM branch_products WHERE id=?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public BranchProduct getBranchProductById(String id) throws SQLException {
        String sql = "SELECT * FROM branch_products WHERE id=?";
        BranchProduct branchProduct = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                branchProduct=new BranchProduct(rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getString("product_id"),
                        rs.getString("branch_id"));
            }
        }
        return branchProduct;
    }
    
    @Override
    public List<BranchProduct> getBranchProductsByProductId(String productId) throws SQLException {
        String sql = "SELECT * FROM branch_products WHERE product_id=?";
        List<BranchProduct> branchProducts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BranchProduct branchProduct=new BranchProduct(rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getString("product_id"),
                        rs.getString("branch_id"));
                branchProducts.add(branchProduct);
            }
        }
        return branchProducts;
    }

    @Override
    public List<BranchProduct> getBranchProductsByBranchId(String branchId) throws SQLException {
        String sql = "SELECT * FROM branch_products WHERE branch_id=?";
        List<BranchProduct> branchProducts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branchId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BranchProduct branchProduct=new BranchProduct(rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getString("product_id"),
                        rs.getString("branch_id"));
                branchProducts.add(branchProduct);
            }
        }
        return branchProducts;
    }

    
    @Override
    public List<BranchProduct> getBranchProductsByBranchIdAndProductId(String branchId, String productId) throws SQLException {
        String sql = "SELECT * FROM branch_products WHERE branch_id=? AND product_id=?";
        List<BranchProduct> branchProducts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branchId);
            pstmt.setString(2, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BranchProduct branchProduct=new BranchProduct(rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getString("product_id"),
                        rs.getString("branch_id"));
                branchProducts.add(branchProduct);
            }
        }
        return branchProducts;
    }

    @Override
    public List<BranchProduct> getAllBranchProducts() throws SQLException {
         String sql = "SELECT * FROM branch_products";
        List<BranchProduct> branchProducts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BranchProduct branchProduct=new BranchProduct(rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getString("product_id"),
                        rs.getString("branch_id"));
                branchProducts.add(branchProduct);
            }
        }
        return branchProducts;
    }

}
