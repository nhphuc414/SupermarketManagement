/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Employee;
import com.ktpm.services.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees(id, employee_name, number, birthday, join_date, username, password, employee_role, brand_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employee.getId());
            pstmt.setString(2, employee.getEmployeeName());
            pstmt.setString(3, employee.getNumber());
            pstmt.setDate(4, new java.sql.Date(employee.getBirthday().getTime()));
            pstmt.setDate(5, new java.sql.Date(employee.getJoinDate().getTime()));
            pstmt.setString(6, employee.getUsername());
            pstmt.setString(7, employee.getPassword());
            pstmt.setString(8, employee.getEmployeeRole().toString());
            pstmt.setString(9, employee.getBrandId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET employee_name = ?, number = ?, birthday = ?, join_date = ?, username = ?, password = ?, employee_role = ?, brand_id = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employee.getEmployeeName());
            pstmt.setString(2, employee.getNumber());
            pstmt.setDate(3, new java.sql.Date(employee.getBirthday().getTime()));
            pstmt.setDate(4, new java.sql.Date(employee.getJoinDate().getTime()));
            pstmt.setString(5, employee.getUsername());
            pstmt.setString(6, employee.getPassword());
            pstmt.setString(7, employee.getEmployeeRole().toString());
            pstmt.setString(8, employee.getBrandId());
            pstmt.setString(9, employee.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteEmployee(String id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Employee getEmployeeById(String id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(rs.getString("id"),
                        rs.getString("employee_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"),
                        rs.getDate("join_date"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Employee.EmployeeRole.valueOf(rs.getString("employee_role")),
                        rs.getString("brand_id"));
            }
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employee employee = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(rs.getString("id"),
                        rs.getString("employee_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"),
                        rs.getDate("join_date"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Employee.EmployeeRole.valueOf(rs.getString("employee_role")),
                        rs.getString("brand_id"));
            }
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("id"),
                        rs.getString("employee_name"),
                        rs.getString("number"),
                        rs.getDate("birthday"),
                        rs.getDate("join_date"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Employee.EmployeeRole.valueOf(rs.getString("employee_role")),
                        rs.getString("brand_id"));
                employees.add(employee);
            }
            return employees;
        }
    }

}
