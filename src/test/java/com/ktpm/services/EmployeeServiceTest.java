/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Employee;
import static com.ktpm.pojo.Employee.EmployeeRole.Manager;
import static com.ktpm.pojo.Employee.EmployeeRole.Employee;
import com.ktpm.services.impl.EmployeeServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
    EmployeeService employeeService = new EmployeeServiceImpl();

    public EmployeeServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {

        conn = JDBCUtils.getConn();

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
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }
    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId("test4");
        employee.setEmployeeName("Test Name Employee");
        employee.setNumber("123456789");
        employee.setBirthday(Date.valueOf("2000-01-01"));
        employee.setJoinDate(Date.valueOf("2020-01-01"));
        employee.setUsername("khanhhuy123");
        employee.setPassword("1234567");
        employee.setEmployeeRole(Manager);
        employee.setBranchId("1");
        employeeService.addEmployee(employee);
        // Verify that the branch was added correctly by retrieving it from the database
        Employee retrievedemployee = employeeService.getEmployeeById("test4");
        assertNotNull(retrievedemployee);
        assertEquals("Test Name Employee", retrievedemployee.getEmployeeName());
        assertEquals("123456789", retrievedemployee.getNumber());
        assertEquals(Date.valueOf("2000-01-01"), retrievedemployee.getBirthday());
        assertEquals(Date.valueOf("2020-01-01"), retrievedemployee.getJoinDate());
        assertEquals("khanhhuy123", retrievedemployee.getUsername());
        assertEquals("1234567", retrievedemployee.getPassword());
        assertEquals(Manager, retrievedemployee.getEmployeeRole());
        assertEquals("1", retrievedemployee.getBranchId());
        
        
    }
    @Test
    public void testUpdateCustomer() throws Exception {
        Employee employee = new Employee();
        employee.setId("test6");
        employee.setEmployeeName("Test Name Employee");
        employee.setNumber("123456789");
        employee.setBirthday(Date.valueOf("2000-01-01"));
        employee.setJoinDate(Date.valueOf("2020-01-01"));
        employee.setUsername("khanhhuy123");
        employee.setPassword("1234567");
        employee.setEmployeeRole(Manager);
        employee.setBranchId("1");
        employeeService.addEmployee(employee);
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
        Employee retrievedemployee = employeeService.getEmployeeById("test6");
        assertNotNull(retrievedemployee);
        
        assertEquals("Upgrade Name Employee", retrievedemployee.getEmployeeName());
        assertEquals("1234567890101", retrievedemployee.getNumber());
        assertEquals(Date.valueOf("2002-01-01"), retrievedemployee.getBirthday());
        assertEquals(Date.valueOf("2022-01-01"), retrievedemployee.getJoinDate());
        assertEquals("khanhhuy679", retrievedemployee.getUsername());
        assertEquals("1234567890", retrievedemployee.getPassword());
        assertEquals(Manager, retrievedemployee.getEmployeeRole());
        
    }
    @Test
    public void testDeleteCustomer() throws Exception {
        employeeService.deleteEmployee("test4");
        employeeService.deleteEmployee("test5");
        employeeService.deleteEmployee("test6");
        Employee retrievedemployee = employeeService.getEmployeeById("test4");
        assertNull(retrievedemployee);
        
    }
    @Test
    public void testGetCustomerById() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Employee retrievedEmployee = employeeService.getEmployeeById("8c04b0e4-462d-43a2-878a-62165029aa82");
        assertNotNull(retrievedEmployee);
        assertEquals("Trần Văn B", retrievedEmployee.getEmployeeName());
        assertEquals("083926502", retrievedEmployee.getNumber());
        assertEquals(Date.valueOf("2023-04-02"), retrievedEmployee.getBirthday());
        assertEquals(Date.valueOf("2023-04-16"), retrievedEmployee.getJoinDate());
        assertEquals("joker", retrievedEmployee.getUsername());
        assertEquals("12", retrievedEmployee.getPassword());
        assertEquals(Employee, retrievedEmployee.getEmployeeRole());
        assertEquals("117316e6-9c70-4e0d-9073-033c6c1a47e2", retrievedEmployee.getBranchId());
    }
    @Test
    public void testGetCustomerByUsernameAndPassword() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Employee retrievedEmployee = employeeService.getEmployeeByUsernameAndPassword("joker","12");
        assertNotNull(retrievedEmployee);
         assertEquals("8c04b0e4-462d-43a2-878a-62165029aa82", retrievedEmployee.getId());
        assertEquals("Trần Văn B", retrievedEmployee.getEmployeeName());
        assertEquals("083926502", retrievedEmployee.getNumber());
        assertEquals(Date.valueOf("2023-04-02"), retrievedEmployee.getBirthday());
        assertEquals(Date.valueOf("2023-04-16"), retrievedEmployee.getJoinDate());
        
        assertEquals(Employee, retrievedEmployee.getEmployeeRole());
        assertEquals("117316e6-9c70-4e0d-9073-033c6c1a47e2", retrievedEmployee.getBranchId());
    }
    @Test
    public void testGetCustomerByBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<Employee> employees = employeeService.getEmployeesByBranchId("117316e6-9c70-4e0d-9073-033c6c1a47e2");
        Employee retrievedEmployee = employees.get(0);
        assertNotNull(retrievedEmployee);
        assertEquals("8c04b0e4-462d-43a2-878a-62165029aa82", retrievedEmployee.getId());
        assertEquals("Trần Văn B", retrievedEmployee.getEmployeeName());
        assertEquals("083926502", retrievedEmployee.getNumber());
        assertEquals(Date.valueOf("2023-04-02"), retrievedEmployee.getBirthday());
        assertEquals(Date.valueOf("2023-04-16"), retrievedEmployee.getJoinDate());
        assertEquals("joker", retrievedEmployee.getUsername());
        assertEquals("12", retrievedEmployee.getPassword());
        assertEquals(Employee, retrievedEmployee.getEmployeeRole());
        assertEquals("117316e6-9c70-4e0d-9073-033c6c1a47e2", retrievedEmployee.getBranchId());
    }
    
    @Test
    public void testGetAllCustomer() throws SQLException {
       
       
        // Add some test data

      
        
        // Call the method being tested
        List<Employee> results = employeeService.getAllEmployees();
        // Assert that the correct number of results were returned
        int sl = results.size();
        employeeService.addEmployee(new Employee("-1", "Huy", "123456", Date.valueOf("2002-04-10"),Date.valueOf("2023-04-10"),"khanhhuy1234","123",Manager,"test1"));
        assertEquals(sl , results.size());

        // Assert that the results match the expected values
        
    }
}
