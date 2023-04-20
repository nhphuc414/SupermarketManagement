/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Discount;
import com.ktpm.pojo.Product;

import com.ktpm.services.impl.DiscountServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
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

    private static Connection conn;
    private static DiscountService discountService;
    private static ProductService productService;
    private Product product;
    private Discount discount;
    public DiscountServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getConn();
        discountService = new DiscountServiceImpl();
        productService = new ProductServiceImpl();
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
    public void setUp() throws SQLException {
        product = new Product("Test", 1000, "Test file", Product.ProductType.Quantity);
        int productId = productService.addProduct(product);
        product.setId(productId);
        
        discount = new Discount(Date.valueOf("2020-01-01"), Date.valueOf("2022-01-01"), 20,product.getId());
        int discountId =discountService.addDiscount(discount);
        discount.setId(discountId);
        
    }

    @AfterEach
    public void tearDown() throws SQLException {
        productService.deleteProduct(product.getId());
        discountService.deleteDiscount(discount.getId());
    }

    @Test
    public void testAddDiscount() throws Exception {
        Discount existDiscount = discountService.getDiscountById(discount.getId());
        assertNotNull(existDiscount);
        assertEquals(existDiscount.getStartDate(), discount.getStartDate());
        assertEquals(existDiscount.getEndDate(), discount.getEndDate());
        assertEquals(existDiscount.getDiscountPercent(), discount.getDiscountPercent());
        assertEquals(existDiscount.getProductId(),discount.getProductId());
    }
    @Test
    public void testGetDiscountById() throws Exception {
        Discount existDiscount = discountService.getDiscountById(discount.getId());
        assertNotNull(existDiscount);
        assertEquals(existDiscount.getStartDate(), discount.getStartDate());
        assertEquals(existDiscount.getEndDate(), discount.getEndDate());
        assertEquals(existDiscount.getDiscountPercent(), discount.getDiscountPercent());
        assertEquals(existDiscount.getProductId(),discount.getProductId());
    }
    @Test
    public void testDeleteDiscount() throws Exception {
        discountService.deleteDiscount(discount.getId());
        Discount existDiscount = discountService.getDiscountById(discount.getId());
        assertNull(existDiscount);
    }
    
    @Test
    public void testUpdateDiscount() throws Exception {
        discount.setStartDate(Date.valueOf("2023-01-01"));
        discount.setEndDate(Date.valueOf("2023-01-01"));
        discount.setDiscountPercent(30);
        discount.setProductId(-15);
        discountService.updateDiscount(discount);
        Discount updateDiscount = discountService.getDiscountById(discount.getId());
        
        assertNotNull(updateDiscount);
        assertEquals(updateDiscount.getStartDate(), discount.getStartDate());
        assertEquals(updateDiscount.getEndDate(), discount.getEndDate());
        assertEquals(updateDiscount.getDiscountPercent(), discount.getDiscountPercent());
        assertEquals(updateDiscount.getProductId(),discount.getProductId());
    }
    
    @Test
    public void testGetDiscountsByProductId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<Discount> discounts = discountService.getDiscountsByProductId(product.getId());
        assertTrue(discounts.stream().anyMatch(d -> d.getId()==discount.getId()));
        int size = discounts.size();
        //add new
        Discount discountAdd = new Discount(Date.valueOf("2021-01-01"), Date.valueOf("2022-01-01"), 10,product.getId());
        int discountAddId =discountService.addDiscount(discountAdd);
        discountAdd.setId(discountAddId);
        //get list after 
        List<Discount> discountsAfter = discountService.getDiscountsByProductId(product.getId());
        assertTrue(discountsAfter.stream().anyMatch(bp -> bp.getId()==discountAdd.getId()));
        int sizeAfter = discountsAfter.size();
        assertEquals(size + 1, sizeAfter);
        discountService.deleteDiscount(discountAdd.getId());
    }
    
    @Test
    public void testgetAllDiscounts() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<Discount> discounts = discountService.getAllDiscounts();
        assertTrue(discounts.stream().anyMatch(d -> d.getId()==discount.getId()));
        int size = discounts.size();
        //add new
        Discount discountAdd = new Discount(Date.valueOf("2021-01-01"), Date.valueOf("2022-01-01"), 10,product.getId());
        int discountAddId =discountService.addDiscount(discountAdd);
        discountAdd.setId(discountAddId);
        //get list after 
        List<Discount> discountsAfter = discountService.getAllDiscounts();
        assertTrue(discountsAfter.stream().anyMatch(bp -> bp.getId()==discountAdd.getId()));
        int sizeAfter = discountsAfter.size();
        assertEquals(size + 1, sizeAfter);
        discountService.deleteDiscount(discountAdd.getId());
    }
    
}
