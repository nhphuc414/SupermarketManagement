///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.ktpm.services;
//
//import com.ktpm.pojo.BranchProduct;
//import com.ktpm.pojo.Customer;
//import com.ktpm.services.impl.CustomerServiceImpl;
//import com.ktpm.utils.JDBCUtils;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
///**
// *
// * @author maikh
// */
//public class CustomerServiceTest {
//    private static Connection conn = null;
//    CustomerService customerService = new CustomerServiceImpl();
//    
//    
//    public CustomerServiceTest() {
//    }
//    
//    @BeforeAll
//    public static void setUpClass() throws SQLException {
//        try {
//            conn = JDBCUtils.getConn();
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//    @AfterAll
//    public static void tearDownClass() {
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(CustomerServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }
//    @Test
//    public void testAddCustomer() throws Exception {
//        Customer customer = new Customer();
//        customer.setId("test3");
//        customer.setCustomerName("Test Customer");
//        customer.setNumber("123 Test");
//        customer.setBirthday(Date.valueOf("2000-01-01"));
//        
//        
//        customerService.addCustomer(customer);
//        // Verify that the branch was added correctly by retrieving it from the database
//        Customer retrievedCustomer = customerService.getCustomerById("test1");
//        assertNotNull(retrievedCustomer);
//        assertEquals("Test Customer", retrievedCustomer.getCustomerName());
//        assertEquals("123 Test", retrievedCustomer.getNumber());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedCustomer.getBirthday());
//        
//    }
//    @Test
//    public void testUpdateCustomer() throws Exception {
//        Customer customer = new Customer();
//        customer.setId("test4");
//        customer.setCustomerName("Test Customer");
//        customer.setNumber("123 Test");
//        customer.setBirthday(Date.valueOf("2000-01-01"));
//        customerService.addCustomer(customer);
//        // Update the branch's information
//        customer.setCustomerName("Upgrade Test Customer");
//        customer.setNumber("456 Test");
//        customer.setBirthday(Date.valueOf("2020-01-01"));
        
//        customerService.updateCustomer(customer);
        // Verify that the branch was updated correctly by retrieving it from the database
//        Customer retrieveCustomer = customerService.getCustomerById("test4");
//        assertNotNull(retrieveCustomer);
//        assertEquals("Upgrade Test Customer", retrieveCustomer.getCustomerName());
//        assertEquals("456 Test", retrieveCustomer.getNumber());
//        assertEquals(Date.valueOf("2020-01-01"), retrieveCustomer.getBirthday());
//    }
    
//    @Test
//    public void testDeleteCustomer() throws Exception {
//        customerService.deleteCustomer("test2");
//        customerService.deleteCustomer("test1");
//        Customer retrievedCustomer = customerService.getCustomerById("test2");
//        assertNull(retrievedCustomer);
//        
//    }
//    @Test
//    public void testGetCustomerById() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        Customer retrievedCustomer = customerService.getCustomerById("1");
//        assertNotNull(retrievedCustomer);
//        assertEquals("Lâm", retrievedCustomer.getCustomerName());
//        assertEquals("123321", retrievedCustomer.getNumber());
//        assertEquals(Date.valueOf("2002-04-10"), retrievedCustomer.getBirthday());
//        
//    }
//    @Test
//    public void testGetAllCustomer() throws SQLException {
//       
//       
//        // Add some test data
//
//      
//        
//        // Call the method being tested
//        List<Customer> results = customerService.getAllCustomers();
//        // Assert that the correct number of results were returned
//        int sl = results.size();
//        customerService.addCustomer(new Customer("5", "Huy", "123456", Date.valueOf("2002-04-10")));
//        assertEquals(sl , results.size());
//
//        // Assert that the results match the expected values
//        
//    }
//    @Test
//    public void testGetCustomerByNumber() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        Customer retrievedCustomer = customerService.getCustomerByNumber("123321");
//        assertNotNull(retrievedCustomer);
//        assertEquals("1", retrievedCustomer.getId());
//        assertEquals("Lâm", retrievedCustomer.getCustomerName());
//        
//        assertEquals(Date.valueOf("2002-04-10"), retrievedCustomer.getBirthday());
//        
//    }
//}
