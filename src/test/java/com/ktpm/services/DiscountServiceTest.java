/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Discount;

import com.ktpm.services.impl.DiscountServiceImpl;
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
public class DiscountServiceTest {
    private static Connection conn = null;
    DiscountService discountService = new DiscountServiceImpl();
    
    
    public DiscountServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        try {
            conn = JDBCUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(DiscountServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DiscountServiceTest.class.getName()).log(Level.SEVERE, null, ex);
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
    public void testAddDiscount() throws Exception {
//        Discount discount = new Discount();
//        discount.setId(22);
//        discount.setStartDate(Date.valueOf("2020-01-01"));
//        discount.setEndDate(Date.valueOf("2022-01-01"));
//        discount.setDiscountPercent(20);
//        discount.setProductId(3);
//        discountService.addDiscount(discount);
        // Verify that the branch was added correctly by retrieving it from the database
        Discount retrievedDiscount;
        retrievedDiscount = discountService.getDiscountById(21);
        assertNotNull(retrievedDiscount);
        assertEquals(Date.valueOf("2020-01-01"), retrievedDiscount.getStartDate());
        assertEquals(Date.valueOf("2022-01-01"), retrievedDiscount.getEndDate());
        assertEquals(20, retrievedDiscount.getDiscountPercent());
        assertEquals(3 , retrievedDiscount.getProductId());
        
    }
}
