/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Employee;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface EmployeeService {

    void addEmployee(Employee employee) throws SQLException;

    void updateEmployee(Employee employee) throws SQLException;

    void deleteEmployee(String id) throws SQLException;

    Employee getEmployeeById(String id) throws SQLException;

    Employee getEmployeeByUsernameAndPassword(String username, String password) throws SQLException;

    List<Employee> getAllEmployees() throws SQLException;

}
