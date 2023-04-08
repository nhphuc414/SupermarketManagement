/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Discount;
import com.ktpm.services.DiscountService;
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
public class DiscountServiceImpl implements DiscountService {

    @Override
    public void addDiscount(Discount discount) throws SQLException {
        String sql = "INSERT INTO discounts (id, start_date, end_date, discount_percent, product_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, discount.getId());
            pstmt.setDate(2, new java.sql.Date(discount.getStartDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(discount.getEndDate().getTime()));
            pstmt.setDouble(4, discount.getDiscountPercent());
            pstmt.setString(5, discount.getProductId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateDiscount(Discount discount) throws SQLException {
        String sql = "UPDATE discounts SET start_date = ?, end_date = ?, discount_percent = ?, product_id = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(discount.getStartDate().getTime()));
            pstmt.setDate(2, new java.sql.Date(discount.getEndDate().getTime()));
            pstmt.setDouble(3, discount.getDiscountPercent());
            pstmt.setString(4, discount.getProductId());
            pstmt.setString(5, discount.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteDiscount(String id) throws SQLException {
        String sql = "DELETE FROM discounts WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Discount getDiscountById(String id) throws SQLException {
        String sql = "SELECT * FROM discounts WHERE id = ?";
        Discount discount = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 discount = new Discount(rs.getString("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getString("product_id"));
             }
        }
        return discount;
    }

    @Override
    public List<Discount> getDiscountsByProductId(String productId) throws SQLException {
        String sql = "SELECT * FROM discounts WHERE product_id = ?";
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Discount discount = new Discount(rs.getString("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getString("product_id"));
                discounts.add(discount);
            }
        }
        return discounts;
    }

    @Override
    public List<Discount> getAllDiscounts() throws SQLException {
        String sql = "SELECT * FROM discounts";
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Discount discount = new Discount(rs.getString("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getString("product_id"));
                discounts.add(discount);
            }
        }
        return discounts;
    }

}
