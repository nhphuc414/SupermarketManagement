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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ad
 */
public class DiscountServiceImpl implements DiscountService {

    @Override
    public int addDiscount(Discount discount) throws SQLException {
        String sql = "INSERT INTO discounts (start_date, end_date, discount_percent, product_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, discount.getStartDate());
            pstmt.setDate(2, discount.getEndDate());
            pstmt.setDouble(3, discount.getDiscountPercent());
            pstmt.setInt(4, discount.getProductId());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        }
        return 0;
    }

    @Override
    public void updateDiscount(Discount discount) throws SQLException {
        String sql = "UPDATE discounts SET start_date = ?, end_date = ?, discount_percent = ?, product_id = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, discount.getStartDate());
            pstmt.setDate(2, discount.getEndDate());
            pstmt.setDouble(3, discount.getDiscountPercent());
            pstmt.setInt(4, discount.getProductId());
            pstmt.setInt(5, discount.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteDiscount(int id) throws SQLException {
        String sql = "DELETE FROM discounts WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Discount getDiscountById(int id) throws SQLException {
        String sql = "SELECT * FROM discounts WHERE id = ?";
        Discount discount = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 discount = new Discount(rs.getInt("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getInt("product_id"));
             }
        }
        return discount;
    }

    @Override
    public List<Discount> getDiscountsByProductId(int productId) throws SQLException {
        String sql = "SELECT * FROM discounts WHERE product_id = ?";
        List<Discount> discounts = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Discount discount = new Discount(rs.getInt("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getInt("product_id"));
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
                Discount discount = new Discount(rs.getInt("id"),
                 rs.getDate("start_date"),
                 rs.getDate("end_date"),
                 rs.getDouble("discount_percent"),
                 rs.getInt("product_id"));
                discounts.add(discount);
            }
        }
        return discounts;
    }

}
