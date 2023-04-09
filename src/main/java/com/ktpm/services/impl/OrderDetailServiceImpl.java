/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.OrderDetail;
import com.ktpm.services.OrderDetailService;
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
public class OrderDetailServiceImpl implements OrderDetailService {

    @Override
    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO order_details(id, quantity, product_id, order_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orderDetail.getId());
            pstmt.setDouble(2, orderDetail.getQuantity());
            pstmt.setInt(3, orderDetail.getProductId());
            pstmt.setString(4, orderDetail.getOrderId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        String sql = "UPDATE order_details SET quantity=?, product_id=?, order_id=? WHERE id=?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, orderDetail.getQuantity());
            pstmt.setInt(2, orderDetail.getProductId());
            pstmt.setString(3, orderDetail.getOrderId());
            pstmt.setString(4, orderDetail.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteOrderDetail(String id) throws SQLException {
        String sql = "DELETE FROM order_details WHERE id=?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public OrderDetail getOrderDetailById(String id) throws SQLException {
        String sql = "SELECT * FROM order_details WHERE id=?";
        OrderDetail orderDetail = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                orderDetail = new OrderDetail(
                        rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getInt("product_id"),
                        rs.getString("order_id"));
            }
        }
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws SQLException {
        String sql = "SELECT * FROM order_detail WHERE order_id=?";
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getInt("product_id"),
                        rs.getString("order_id"));
                orderDetails.add(orderDetail);
            }
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByProductId(int productId) throws SQLException {
        String sql = "SELECT * FROM order_detail WHERE order_id=?";
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getInt("product_id"),
                        rs.getString("order_id"));
                orderDetails.add(orderDetail);
            }
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() throws SQLException {
        String sql = "SELECT * FROM order_details";
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(
                        rs.getString("id"),
                        rs.getDouble("quantity"),
                        rs.getInt("product_id"),
                        rs.getString("order_id"));
                orderDetails.add(orderDetail);
            }
        }
        return orderDetails;
    }
}
