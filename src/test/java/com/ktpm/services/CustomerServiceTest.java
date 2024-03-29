/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Customer;
import com.ktpm.services.impl.CustomerServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
public class CustomerServiceTest {

    private static Connection conn = null;
    private static CustomerService customerService;
    private Customer customer ;
    public CustomerServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getConn();
        customerService = new CustomerServiceImpl();

    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        customer = new Customer();
        customer.setId("test");
        customer.setCustomerName("Test Customer");
        customer.setNumber("123 Test");
        customer.setBirthday(Date.valueOf("2000-01-01"));
        customerService.addCustomer(customer);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        customerService.deleteCustomer(customer.getId());
    }

    @Test
    public void testAddCustomer() throws Exception {
        // Verify that the branch was added correctly by retrieving it from the database
        Customer retrievedCustomer = customerService.getCustomerById(customer.getId());
        assertNotNull(retrievedCustomer);
        assertEquals("Test Customer", retrievedCustomer.getCustomerName());
        assertEquals("123 Test", retrievedCustomer.getNumber());
        assertEquals(Date.valueOf("2000-01-01"), retrievedCustomer.getBirthday());
        customerService.deleteCustomer(customer.getId());

    }

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
//
//        customerService.updateCustomer(customer);
//
//        Customer retrieveCustomer = customerService.getCustomerById("test4");
//        assertNotNull(retrieveCustomer);
//        assertEquals("Upgrade Test Customer", retrieveCustomer.getCustomerName());
//        assertEquals("456 Test", retrieveCustomer.getNumber());
//        assertEquals(Date.valueOf("2020-01-01"), retrieveCustomer.getBirthday());
//        customerService.deleteCustomer("test4");
//    }
//
//    @Test
//    public void testDeleteCustomer() throws Exception {
//        Customer customer = new Customer();
//        customer.setId("test4");
//        customer.setCustomerName("Test Customer");
//        customer.setNumber("123 Test");
//        customer.setBirthday(Date.valueOf("2000-01-01"));
//        customerService.addCustomer(customer);
//        customerService.deleteCustomer("test4");
//        Customer retrievedCustomer = customerService.getCustomerById("test4");
//        assertNull(retrievedCustomer);
//
//    }
//
//    @Test
//    public void testGetCustomerById() throws Exception {
//        Customer customer = new Customer();
//        customer.setId("test4");
//        customer.setCustomerName("Test Customer");
//        customer.setNumber("123 Test");
//        customer.setBirthday(Date.valueOf("2000-01-01"));
//        customerService.addCustomer(customer);
//        Customer retrievedCustomer = customerService.getCustomerById("test4");
//        assertNotNull(retrievedCustomer);
//        assertEquals("Test Customer", retrievedCustomer.getCustomerName());
//        assertEquals("123 Test", retrievedCustomer.getNumber());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedCustomer.getBirthday());
//        customerService.deleteCustomer("test4");
//
//    }
//
//    @Test
//    public void testGetAllCustomer() throws SQLException {
//        Customer customer = new Customer();
//        customer.setId("test4");
//        customer.setCustomerName("Test Customer");
//        customer.setNumber("123 Test");
//        customer.setBirthday(Date.valueOf("2000-01-01"));
//        customerService.addCustomer(customer);
//        int sl = customerService.getAllCustomers().size();
//        customerService.addCustomer(new Customer("test5", "Huy", "123456", Date.valueOf("2002-04-10")));
//        int s2 = customerService.getAllCustomers().size();
//        assertEquals(sl+1, s2);
//        customerService.deleteCustomer("test4");
//        customerService.deleteCustomer("test5");
//        // Assert that the results match the expected values
//    }

    @Test
    public void testGetCustomerByNumber() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Customer retrievedCustomer = customerService.getCustomerByNumber("123 Test");
        assertNotNull(retrievedCustomer);
        assertEquals(customer.getId(), retrievedCustomer.getId());
        assertEquals(customer.getCustomerName(), retrievedCustomer.getCustomerName());
        assertEquals(customer.getBirthday(), retrievedCustomer.getBirthday());
    }
}
