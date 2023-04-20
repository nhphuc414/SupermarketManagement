/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.OrderDetail;
import com.ktpm.services.impl.OrderDetailServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
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
public class OrderDetailServiceTest {
    private static Connection conn = null;
    private static OrderDetailService orderDetailService;
    
    
    public OrderDetailServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getConn();
        orderDetailService = new OrderDetailServiceImpl();
    }
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDetailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
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
    public void testAddOrderDetail() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId("6");
        orderDetail.setQuantity(20);
        orderDetail.setProductId(3);
        orderDetail.setOrderId("2");
        
        
        orderDetailService.addOrderDetail(orderDetail);
        // Verify that the branch was added correctly by retrieving it from the database
        OrderDetail retrievedOrderDetail = orderDetailService.getOrderDetailById("1");
        assertNotNull(retrievedOrderDetail);
        assertEquals(20, retrievedOrderDetail.getQuantity());
        assertEquals(3, retrievedOrderDetail.getProductId());
        assertEquals("2", retrievedOrderDetail.getOrderId());
        
    }
        @Test
        public void testUpdateOrderDetail() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId("5");
        orderDetail.setQuantity(30);
        orderDetail.setProductId(12);
        orderDetail.setOrderId("1");
        orderDetailService.addOrderDetail(orderDetail);
        // Update the branch's information
        orderDetail.setQuantity(30);
        orderDetail.setProductId(14);
        orderDetail.setOrderId("2");
        orderDetailService.updateOrderDetail(orderDetail);
         
        OrderDetail retrievedOrderDetail = orderDetailService.getOrderDetailById("5");
        assertNotNull(retrievedOrderDetail);
        assertEquals(30, retrievedOrderDetail.getQuantity());
        assertEquals(14, retrievedOrderDetail.getProductId());
        assertEquals("2", retrievedOrderDetail.getOrderId());
       
    }  
    @Test
    public void testDeleteOrderDetail() throws Exception {
        orderDetailService.deleteOrderDetail("5");
        
        OrderDetail retrievedOrderDetail = orderDetailService.getOrderDetailById("5");
        assertNull(retrievedOrderDetail);
    }
        @Test
    public void testGetOrderById() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        OrderDetail retrievedOrder = orderDetailService.getOrderDetailById("1");
        assertNotNull(retrievedOrder);
        assertEquals(20, retrievedOrder.getQuantity());
        assertEquals(3, retrievedOrder.getProductId());
        assertEquals("2", retrievedOrder.getOrderId());
        
    }
    @Test
    public void testGetOrdersDetailByOrderId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List <OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId("2");
        OrderDetail retrievedOrder = orderDetails.get(0);
        assertNotNull(retrievedOrder);
        assertEquals("1", retrievedOrder.getId());
        assertEquals(20, retrievedOrder.getQuantity());
        assertEquals(3, retrievedOrder.getProductId());
        
        
        
    }
   
     @Test
    public void testGetOrderDetailsByOrderId() throws SQLException {
       
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId("1");
        assertEquals(1, orderDetails.size());
    }
}
