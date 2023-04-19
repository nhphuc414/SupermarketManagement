/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.BranchProduct;
import com.ktpm.services.impl.BranchProductServiceImpl;
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
public class BranchProductServiceTest {
    private static Connection conn = null;
    BranchProductService branchProductService = new BranchProductServiceImpl();
    
    
    public BranchProductServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getTestConn();
        
    }
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchProductServiceTest.class.getName()).log(Level.SEVERE, null, ex);
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
    public void testAddBranchProduct() throws Exception {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId("add");
        branchProduct.setQuantity(40);
        branchProduct.setProductId(-16);
        branchProduct.setBranchId("test");
        branchProductService.addBranchProduct(branchProduct);
        // Verify that the branch was added correctly by retrieving it from the database
        BranchProduct retrievedBranchproduct = branchProductService.getBranchProductById("add");
        assertNotNull(retrievedBranchproduct);
        assertEquals(40, retrievedBranchproduct.getQuantity());
        assertEquals(-16, retrievedBranchproduct.getProductId());
        assertEquals("test", retrievedBranchproduct.getBranchId());
        
    }
    @Test
    public void testUpdateBranchProduct() throws Exception {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId("test6");
        branchProduct.setQuantity(20);
        branchProduct.setProductId(-16);
        branchProduct.setBranchId("test");
        branchProductService.addBranchProduct(branchProduct);
        // Update the branch's information
        branchProduct.setQuantity(30);
        branchProductService.updateBranchProduct(branchProduct);
        // Verify that the branch was updated correctly by retrieving it from the database
        BranchProduct retrievedbranchProduct = branchProductService.getBranchProductById("test6");
        assertNotNull(retrievedbranchProduct);
        
        assertEquals(30, retrievedbranchProduct.getQuantity());
        
        
    }
    @Test
    public void testGetBranchProductById() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        BranchProduct retrievedBranchProduct = branchProductService.getBranchProductById("add");
        assertNotNull(retrievedBranchProduct);
        assertEquals(40, retrievedBranchProduct.getQuantity());
        assertEquals(-16, retrievedBranchProduct.getProductId());
        assertEquals("test", retrievedBranchProduct.getBranchId());
    }
    @Test
    public void testGetBranchProductByProductIdAndBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        BranchProduct retrievedBranchProduct = branchProductService.getBranchProductsByBranchIdAndProductId("test",-16);
        assertNotNull(retrievedBranchProduct);
        assertEquals(30, retrievedBranchProduct.getQuantity());
        assertEquals("test6", retrievedBranchProduct.getId());
        
    }
     @Test
    public void testGetBranchProductsByProductId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List <BranchProduct> branchProducts = branchProductService.getBranchProductsByProductId(-16);
        BranchProduct retrievedBranchProduct = branchProducts.get(0);
        assertNotNull(retrievedBranchProduct);
        assertEquals(40, retrievedBranchProduct.getQuantity());
        assertEquals("test", retrievedBranchProduct.getBranchId());
    }
    
    @Test
    public void testGetBranchProductsByBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List <BranchProduct> branchProducts = branchProductService.getBranchProductsByBranchId("test");
        BranchProduct retrievedBranchProduct = branchProducts.get(0);
        assertNotNull(retrievedBranchProduct);
        assertEquals(30, retrievedBranchProduct.getQuantity());
        assertEquals(-16, retrievedBranchProduct.getProductId());
    
    }
    @Test
    public void testDeleteBranchProduct() throws Exception {
        branchProductService.deleteBranchProduct("add");
        branchProductService.deleteBranchProduct("test6");
        BranchProduct retrievedbranchProduct =  branchProductService.getBranchProductById("add");
        assertNull(retrievedbranchProduct);
    }
    
    
   
    
}
