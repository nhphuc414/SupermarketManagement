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
        try {
            conn = JDBCUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BranchProductServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
//    @Test
//    public void testAddBranchProduct() throws Exception {
//        BranchProduct branchProduct = new BranchProduct();
//        branchProduct.setId("test5");
//        branchProduct.setQuantity(40);
//        branchProduct.setProductId(3);
//        branchProduct.setBranchId("117316e6-9c70-4e0d-9073-033c6c1a47e2");
//        branchProductService.addBranchProduct(branchProduct);
//        // Verify that the branch was added correctly by retrieving it from the database
//        BranchProduct retrievedBranchproduct = branchProductService.getBranchProductById("test5");
//        assertNotNull(retrievedBranchproduct);
//        assertEquals(40, retrievedBranchproduct.getQuantity());
//        assertEquals(3, retrievedBranchproduct.getProductId());
//        assertEquals("117316e6-9c70-4e0d-9073-033c6c1a47e2", retrievedBranchproduct.getBranchId());
//        
//    }
//    @Test
//    public void testUpdateBranchProduct() throws Exception {
//        BranchProduct branchProduct = new BranchProduct();
//        branchProduct.setId("test6");
//        branchProduct.setQuantity(20);
//        branchProduct.setProductId(2);
//        branchProduct.setBranchId("49aa3541-ae0e-43f8-8800-548d184863e8");
//        
//        branchProductService.addBranchProduct(branchProduct);
//        // Update the branch's information
//        branchProduct.setQuantity(30);
//        
//         
//        
//        // Verify that the branch was updated correctly by retrieving it from the database
//        BranchProduct retrievedbranchProduct = branchProductService.getBranchProductById("test6");
//        assertNotNull(retrievedbranchProduct);
//        
//        assertEquals(20, retrievedbranchProduct.getQuantity());
//        
//        
//    }
//    @Test
//    public void testDeleteBranchProduct() throws Exception {
//        branchProductService.deleteBranchProduct("test1");
//        branchProductService.deleteBranchProduct("test3");
//        branchProductService.deleteBranchProduct("test4");
//        branchProductService.deleteBranchProduct("test5");
//        branchProductService.deleteBranchProduct("test6");
//       
//        BranchProduct retrievedbranchProduct =  branchProductService.getBranchProductById("test4");
//        assertNull(retrievedbranchProduct);
//        
//    }
//    @Test
//    public void testGetBranchProductById() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        BranchProduct retrievedBranchProduct = branchProductService.getBranchProductById("1");
//        assertNotNull(retrievedBranchProduct);
//        assertEquals(50, retrievedBranchProduct.getQuantity());
//        assertEquals(1, retrievedBranchProduct.getProductId());
//        assertEquals("1", retrievedBranchProduct.getBranchId());
//    }
//    @Test
//    public void testGetBranchProductByProductIdAndBranchId() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        BranchProduct retrievedBranchProduct = branchProductService.getBranchProductsByBranchIdAndProductId("1",1);
//        assertNotNull(retrievedBranchProduct);
//        assertEquals(68, retrievedBranchProduct.getQuantity());
//        assertEquals("436e56d9-924b-4834-9536-fefc987ce856", retrievedBranchProduct.getId());
//        
//    }
//    @Test
//    public void testGetBranchProductsByProductId() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        List <BranchProduct> branchProducts = branchProductService.getBranchProductsByProductId(1);
//        BranchProduct retrievedBranchProduct = branchProducts.get(0);
//        assertNotNull(retrievedBranchProduct);
//        assertEquals(50, retrievedBranchProduct.getQuantity());
//        assertEquals("1", retrievedBranchProduct.getBranchId());
//    }
//    @Test
//    public void testGetBranchProductsByBranchId() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        List <BranchProduct> branchProducts = branchProductService.getBranchProductsByBranchId("1");
//        BranchProduct retrievedBranchProduct = branchProducts.get(0);
//        assertNotNull(retrievedBranchProduct);
//        assertEquals(50, retrievedBranchProduct.getQuantity());
//        assertEquals(1, retrievedBranchProduct.getProductId());
//    
//    }
//    
//    @Test
//    public void testGetAllCustomer() throws SQLException {
//       
//       
//        // Add some test data
//
//      
//        
//        // Call the method being tested
//        List<BranchProduct> results = branchProductService.getAllBranchProducts();
//        // Assert that the correct number of results were returned
//        int sl = results.size();
//        branchProductService.addBranchProduct(new BranchProduct("3",30,2,"117316e6-9c70-4e0d-9073-033c6c1a47e2"));
//        assertEquals(sl , results.size());
//
//        // Assert that the results match the expected values
//        
//    }
    
}
