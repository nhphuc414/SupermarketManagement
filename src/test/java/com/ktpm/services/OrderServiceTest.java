/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.services.impl.OrderServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
/**
 *
 * @author maikh
 */
public class OrderServiceTest {
    private static Connection conn = null;
    private static OrderService orderService;
    
    
    public OrderServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
            conn = JDBCUtils.getConn();
            orderService = new OrderServiceImpl();
        
    }
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
//    @Test
//    public void testAddOrder() throws Exception {
//        Order order = new Order();
//        order.setId("1");
//        order.setOrderDate(Date.valueOf("2000-01-01"));
//        order.setCashReceived(20000);
//        order.setEmployeeId("8c04b0e4-462d-43a2-878a-62165029aa82");
//        order.setCustomerId("2");
//        
//        orderService.addOrder(order);
//        // Verify that the branch was added correctly by retrieving it from the database
//        Order retrievedOrder = orderService.getOrderById("1");
//        assertNotNull(retrievedOrder);
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals(20000, retrievedOrder.getCashReceived());
//        assertEquals("8c04b0e4-462d-43a2-878a-62165029aa82", retrievedOrder.getEmployeeId());
//        assertEquals("2", retrievedOrder.getCustomerId());
//        
//        
//    }
//        @Test
//        public void testUpdateOrder() throws Exception {
//          Order order = new Order();
//        order.setId("2");
//        order.setOrderDate(Date.valueOf("2020-01-01"));
//        order.setCashReceived(10000);
//        order.setEmployeeId("8c04b0e4-462d-43a2-878a-62165029aa82");
//        order.setCustomerId("2");
//        orderService.addOrder(order);
//        // Update the branch's information
//        order.setOrderDate(Date.valueOf("2022-01-01"));
//        order.setCashReceived(10000);
//        
//        orderService.updateOrder(order);
//        // Verify that the branch was updated correctly by retrieving it from the database
//        Order retrievedOrder = orderService.getOrderById("2");
//        assertNotNull(retrievedOrder);
//        assertEquals(Date.valueOf("2022-01-01"), retrievedOrder.getOrderDate());
//        assertEquals(10000, retrievedOrder.getCashReceived());
//       
//    }
//    
//    @Test
//    public void testDeleteOrder() throws Exception {
//        orderService.deleteOrder("2");
//        
//        Order retrievedOrder = orderService.getOrderById("2");
//        assertNull(retrievedOrder);
//        
//    }
//    @Test
//    public void testGetOrderById() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        Order retrievedOrder = orderService.getOrderById("1");
//        assertNotNull(retrievedOrder);
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals(20000, retrievedOrder.getCashReceived());
//        assertEquals("2", retrievedOrder.getCustomerId());
//        
//    }
//    @Test
//    public void testGetOrdersByCustomerId() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        List<Order> orders = orderService.getOrdersByCustomerId("2");
//        Order retrievedOrder = orders.get(0);
//        assertNotNull(retrievedOrder);
//        assertEquals("1", retrievedOrder.getId());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals(20000, retrievedOrder.getCashReceived());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals("8c04b0e4-462d-43a2-878a-62165029aa82", retrievedOrder.getEmployeeId());
//        
//        
//    }
//    @Test
//    public void testGetOrdersByEmloyeeId() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        List<Order> orders = orderService.getOrdersByEmployeeId("8c04b0e4-462d-43a2-878a-62165029aa82");
//        Order retrievedOrder = orders.get(0);
//        assertNotNull(retrievedOrder);
//        assertEquals("1", retrievedOrder.getId());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals(20000, retrievedOrder.getCashReceived());
//        assertEquals(Date.valueOf("2000-01-01"), retrievedOrder.getOrderDate());
//        assertEquals("2", retrievedOrder.getCustomerId());
//        
//        
//    }
//    @Test
//    public void testGetAllOrder() throws SQLException {
//       
//       
//        // Add some test data
//
//      
//        
//        // Call the method being tested
//        List<Order> results = orderService.getAllOrders();
//        // Assert that the correct number of results were returned
//        int sl = results.size();
//        orderService.addOrder(new Order("2", Date.valueOf("2023-04-10"),10000,"8efa6257-e9f4-4b43-b76b-cc76debfd4ea","3"));
//        assertEquals(sl , results.size());
//
//        // Assert that the results match the expected values
//        
//    }
}
