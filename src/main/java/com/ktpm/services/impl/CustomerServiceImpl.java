/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Customer;
import com.ktpm.services.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (id, customer_name, number, birthday) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getId());
            pstmt.setString(2, customer.getCustomerName());
            pstmt.setString(3, customer.getNumber());
            pstmt.setDate(4, customer.getBirthday());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET customer_name = ?, number = ?, birthday = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getNumber());
            pstmt.setDate(3, customer.getBirthday());
            pstmt.setString(4, customer.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteCustomer(String id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Customer getCustomerById(String id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        Customer customer = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                customer = new Customer(rs.getString("id"),
                        rs.getString("customer_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"));
            } 
        }
        return customer;
    }

    @Override
    public Customer getCustomerByNumber(String number) throws SQLException {
        String sql = "SELECT * FROM customers WHERE number = ?";
        Customer customer = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                customer = new Customer(rs.getString("id"),
                        rs.getString("customer_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"));
            } 
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(rs.getString("id"),
                        rs.getString("customer_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"));
                customers.add(customer);
            } 
        }
        return customers;
    }

}
