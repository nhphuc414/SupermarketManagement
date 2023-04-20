/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.Employee;
import static com.ktpm.pojo.Employee.EmployeeRole.Manager;
import static com.ktpm.pojo.Employee.EmployeeRole.Employee;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.EmployeeServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author maikh
 */
public class EmployeeServiceTest {

    private static Connection conn = null;
    private static EmployeeService employeeService;
    private static BranchService branchService;
    private Branch branch;
    private Employee employee;
    public EmployeeServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getConn();
        employeeService = new EmployeeServiceImpl();
        branchService = new BranchServiceImpl();
    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        branch = new Branch("test", "Test Branch Product", "Test Branch Product address");
        branchService.addBranch(branch);
        employee = new Employee("Test Name", "Test Number", Date.valueOf("2020-01-01"), Date.valueOf("2002-01-01"), "testemployee", "test", Employee, branch.getId());
        employeeService.addEmployee(employee);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        employeeService.deleteEmployee(employee.getId());
        branchService.deleteBranch(branch.getId());
    }
    @Test
    public void testAddEmployee() throws Exception {
        // Verify that the branch was added correctly by retrieving it from the database
        Employee retrievedemployee = employeeService.getEmployeeById(employee.getId());
        assertNotNull(retrievedemployee);
        assertEquals(employee.getEmployeeName(), retrievedemployee.getEmployeeName());
        assertEquals(employee.getNumber(), retrievedemployee.getNumber());
        assertEquals(employee.getBirthday(), retrievedemployee.getBirthday());
        assertEquals(employee.getJoinDate(), retrievedemployee.getJoinDate());
        assertEquals(employee.getUsername(), retrievedemployee.getUsername());
        assertEquals(employee.getPassword(), retrievedemployee.getPassword());
        assertEquals(employee.getEmployeeRole(), retrievedemployee.getEmployeeRole());
        assertEquals(employee.getBranchId(), retrievedemployee.getBranchId());
    }
    @Test
    public void testUpdateEmployee() throws Exception {
        // Update the branch's information
        employee.setEmployeeName("Upgrade Name Employee");
        employee.setNumber("1234567890101");
        employee.setBirthday(Date.valueOf("2002-01-01"));
        employee.setJoinDate(Date.valueOf("2022-01-01"));
        employee.setUsername("khanhhuy679");
        employee.setPassword("1234567890");
        employee.setEmployeeRole(Manager);
        employeeService.updateEmployee(employee);
        
        // Verify that the branch was updated correctly by retrieving it from the database
        Employee retrievedemployee = employeeService.getEmployeeById(employee.getId());
        assertNotNull(retrievedemployee);
        assertEquals(employee.getEmployeeName(), retrievedemployee.getEmployeeName());
        assertEquals(employee.getNumber(), retrievedemployee.getNumber());
        assertEquals(employee.getBirthday(), retrievedemployee.getBirthday());
        assertEquals(employee.getJoinDate(), retrievedemployee.getJoinDate());
        assertEquals(employee.getUsername(), retrievedemployee.getUsername());
        assertEquals(employee.getPassword(), retrievedemployee.getPassword());
        assertEquals(employee.getEmployeeRole(), retrievedemployee.getEmployeeRole());
        assertEquals(employee.getBranchId(), retrievedemployee.getBranchId());
        
    }
    @Test
    public void testDeleteEmployee() throws Exception {
        employeeService.deleteEmployee(employee.getId());
        Employee retrievedemployee = employeeService.getEmployeeById(employee.getId());
        assertNull(retrievedemployee);
        
    }
    @Test
    public void testGetEmployeeById() throws Exception {
        Employee retrievedemployee = employeeService.getEmployeeById(employee.getId());
        assertNotNull(retrievedemployee);
        assertEquals(employee.getEmployeeName(), retrievedemployee.getEmployeeName());
        assertEquals(employee.getNumber(), retrievedemployee.getNumber());
        assertEquals(employee.getBirthday(), retrievedemployee.getBirthday());
        assertEquals(employee.getJoinDate(), retrievedemployee.getJoinDate());
        assertEquals(employee.getUsername(), retrievedemployee.getUsername());
        assertEquals(employee.getPassword(), retrievedemployee.getPassword());
        assertEquals(employee.getEmployeeRole(), retrievedemployee.getEmployeeRole());
        assertEquals(employee.getBranchId(), retrievedemployee.getBranchId());
    }
    @Test
    public void testGetCustomerByUsernameAndPassword() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Employee retrievedemployee = employeeService.getEmployeeByUsernameAndPassword(employee.getUsername(),employee.getPassword());
        assertNotNull(retrievedemployee);
        assertEquals(employee.getEmployeeName(), retrievedemployee.getEmployeeName());
        assertEquals(employee.getNumber(), retrievedemployee.getNumber());
        assertEquals(employee.getBirthday(), retrievedemployee.getBirthday());
        assertEquals(employee.getJoinDate(), retrievedemployee.getJoinDate());
        assertEquals(employee.getUsername(), retrievedemployee.getUsername());
        assertEquals(employee.getPassword(), retrievedemployee.getPassword());
        assertEquals(employee.getEmployeeRole(), retrievedemployee.getEmployeeRole());
        assertEquals(employee.getBranchId(), retrievedemployee.getBranchId());
    }
    @Test
    public void testGetEmployeeByBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<Employee> employees = employeeService.getEmployeesByBranchId(branch.getId());
        
        assertTrue(employees.stream().anyMatch(e -> e.getId().equals(employee.getId())));
        int size = employees.size();
        //add new 
        Employee testAddEmployee = new Employee("Test Add Name", "Test Add Number", Date.valueOf("2020-01-01"), Date.valueOf("2002-01-01"), "testaddemployee", "test", Employee, branch.getId());
        employeeService.addEmployee(testAddEmployee);
        
        List<Employee> employeesAfter = employeeService.getEmployeesByBranchId(branch.getId());
        assertTrue(employeesAfter.stream().anyMatch(e -> e.getId().equals(testAddEmployee.getId())));
        int sizeAfter = employeesAfter.size();
        assertEquals(size+1, sizeAfter);
        employeeService.deleteEmployee(testAddEmployee.getId());
    }
    
    @Test
    public void testGetAllEmployee() throws SQLException {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<Employee> employees = employeeService.getAllEmployees();
        assertTrue(employees.stream().anyMatch(e -> e.getId().equals(employee.getId())));
        int size = employees.size();
        //add new 
        Employee testAddEmployee = new Employee("Test Add Name", "Test Add Number", Date.valueOf("2020-01-01"), Date.valueOf("2002-01-01"), "testaddemployee", "test", Employee, branch.getId());
        employeeService.addEmployee(testAddEmployee);
        
        List<Employee> employeesAfter = employeeService.getAllEmployees();
        assertTrue(employeesAfter.stream().anyMatch(e -> e.getId().equals(testAddEmployee.getId())));
        int sizeAfter = employeesAfter.size();
        assertEquals(size+1, sizeAfter);
        employeeService.deleteEmployee(testAddEmployee.getId());
        
    }
}
