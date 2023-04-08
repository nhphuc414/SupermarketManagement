/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Order;
import com.ktpm.services.OrderService;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ad
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (id, order_date, cash_received, employee_id, customer_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, order.getId());
            pstmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            pstmt.setDouble(3, order.getCashReceived());
            pstmt.setString(4, order.getEmployeeId());
            pstmt.setString(5, order.getCustomerId());
            pstmt.executeUpdate();

        }
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET order_date = ?, cash_received = ?, employee_id = ?, customer_id = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            pstmt.setDouble(2, order.getCashReceived());
            pstmt.setString(3, order.getEmployeeId());
            pstmt.setString(4, order.getCustomerId());
            pstmt.setString(5, order.getId());
            pstmt.executeUpdate();

        }
    }

    @Override
    public void deleteOrder(String id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Order getOrderById(String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Order order = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                order = new Order(rs.getString("id"),
                        rs.getDate("order_date"),
                        rs.getDouble("cash_received"),
                        rs.getString("employee_id"),
                        rs.getString("customer_id"));
            }
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomerId(String customerId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getString("id"),
                        rs.getDate("order_date"),
                        rs.getDouble("cash_received"),
                        rs.getString("employee_id"),
                        rs.getString("customer_id"));
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByEmployeeId(String employeeId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE employee_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getString("id"),
                        rs.getDate("order_date"),
                        rs.getDouble("cash_received"),
                        rs.getString("employee_id"),
                        rs.getString("customer_id"));
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_date BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getString("id"),
                        rs.getDate("order_date"),
                        rs.getDouble("cash_received"),
                        rs.getString("employee_id"),
                        rs.getString("customer_id"));
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getString("id"),
                        rs.getDate("order_date"),
                        rs.getDouble("cash_received"),
                        rs.getString("employee_id"),
                        rs.getString("customer_id"));
                orders.add(order);
            }
        }
        return orders;
    }

}
