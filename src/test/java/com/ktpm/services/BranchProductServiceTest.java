/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.Product;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
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
    private static BranchProductService branchProductService;
    private static BranchService branchService;
    private static ProductService productService;
    private Product product;
    private Branch branch;
    private BranchProduct branchProduct;

    public BranchProductServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getTestConn();
        branchProductService = new BranchProductServiceImpl();
        branchService = new BranchServiceImpl();
        productService = new ProductServiceImpl();

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
    public void setUp() throws SQLException {
        product = new Product("Test", 1000, "Test file", Product.ProductType.Quantity);
        int productId = productService.addProduct(product);
        product.setId(productId);
        branch = new Branch("test", "Test Branch Product", "Test Branch Product address");
        branchService.addBranch(branch);

        branchProduct = new BranchProduct();
        branchProduct.setId("add");
        branchProduct.setQuantity(40);
        branchProduct.setProductId(product.getId());
        branchProduct.setBranchId(branch.getId());
        branchProductService.addBranchProduct(branchProduct);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        branchProductService.deleteBranchProduct(branchProduct.getId());
        productService.deleteProduct(product.getId());
        branchService.deleteBranch(branch.getId());

    }

    @Test
    public void testAddBranchProduct() throws Exception {
        
        // Verify that the branch was added correctly by retrieving it from the database
        BranchProduct retrievedBranchproduct = branchProductService.getBranchProductById(branchProduct.getId());
        assertNotNull(retrievedBranchproduct);
        assertEquals(branchProduct.getQuantity(), retrievedBranchproduct.getQuantity());
        assertEquals(branchProduct.getProductId(), retrievedBranchproduct.getProductId());
        assertEquals(branchProduct.getBranchId(), retrievedBranchproduct.getBranchId());
        
    }

    @Test
    public void testUpdateBranchProduct() throws Exception {
        branchProduct.setQuantity(30);
        branchProductService.updateBranchProduct(branchProduct);
        // Verify that the branch was updated correctly by retrieving it from the database
        BranchProduct retrievedbranchProduct = branchProductService.getBranchProductById(branchProduct.getId());
        assertNotNull(retrievedbranchProduct);
        assertEquals(30, retrievedbranchProduct.getQuantity());
    }
    
    @Test
    public void testDeleteBranchProduct() throws Exception { 
        branchProductService.deleteBranchProduct(branchProduct.getId());
        BranchProduct retrievedBranchproduct = branchProductService.getBranchProductById(branchProduct.getId());
        assertNull(retrievedBranchproduct);
    }
    @Test
    public void testGetBranchProductById() throws Exception {
        // Verify that the branch was added correctly by retrieving it from the database
        BranchProduct retrievedBranchproduct = branchProductService.getBranchProductById(branchProduct.getId());
        assertNotNull(retrievedBranchproduct);
        assertEquals(branchProduct.getQuantity(), retrievedBranchproduct.getQuantity());
        assertEquals(branchProduct.getProductId(), retrievedBranchproduct.getProductId());
        assertEquals(branchProduct.getBranchId(), retrievedBranchproduct.getBranchId());
    }
    

    @Test
    public void testGetBranchProductByProductIdAndBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        BranchProduct retrievedBranchProduct = branchProductService.getBranchProductsByBranchIdAndProductId(branch.getId(), product.getId());
        assertNotNull(retrievedBranchProduct);
        assertEquals(branchProduct.getQuantity(), retrievedBranchProduct.getQuantity());
        assertEquals(branchProduct.getId(), retrievedBranchProduct.getId());

    }
    
    @Test
    public void testGetAllBranchProducts() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<BranchProduct> branchProducts = branchProductService.getAllBranchProducts();
        assertTrue(branchProducts.stream().anyMatch(bp -> bp.getId().equals(branchProduct.getId())));
        int size = branchProducts.size();
        //add new
        BranchProduct branchProductAdd = new BranchProduct();
        branchProductAdd.setId("testadd");
        branchProductAdd.setQuantity(40);
        branchProductAdd.setProductId(product.getId());
        branchProductAdd.setBranchId(branch.getId());
        //get list after 
        branchProductService.addBranchProduct(branchProductAdd);
        List<BranchProduct> branchProductsAfter = branchProductService.getAllBranchProducts();
        assertTrue(branchProductsAfter.stream().anyMatch(bp -> bp.getId().equals(branchProductAdd.getId())));
        int sizeAfter = branchProductsAfter.size();
        assertEquals(size + 1, sizeAfter);
        branchProductService.deleteBranchProduct(branchProductAdd.getId());
    }
    @Test
    public void testGetBranchProductsByProductId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<BranchProduct> branchProducts = branchProductService.getBranchProductsByProductId(product.getId());
        assertTrue(branchProducts.stream().anyMatch(bp -> bp.getId().equals(branchProduct.getId())));
        int size = branchProducts.size();
        //add new
        BranchProduct branchProductAdd = new BranchProduct();
        branchProductAdd.setId("testadd");
        branchProductAdd.setQuantity(40);
        branchProductAdd.setProductId(product.getId());
        branchProductAdd.setBranchId(branch.getId());
        //get list after 
        branchProductService.addBranchProduct(branchProductAdd);
        List<BranchProduct> branchProductsAfter = branchProductService.getBranchProductsByProductId(product.getId());
        assertTrue(branchProductsAfter.stream().anyMatch(bp -> bp.getId().equals(branchProductAdd.getId())));
        int sizeAfter = branchProductsAfter.size();

        assertEquals(size + 1, sizeAfter);
        branchProductService.deleteBranchProduct(branchProductAdd.getId());
    }

    @Test
    public void testGetBranchProductsByBranchId() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        List<BranchProduct> branchProducts = branchProductService.getBranchProductsByBranchId(branch.getId());
        int size = branchProducts.size();
        assertTrue(branchProducts.stream().anyMatch(bp -> bp.getId().equals(branchProduct.getId())));
        BranchProduct branchProductAdd = new BranchProduct();
        branchProductAdd.setId("testadd");
        branchProductAdd.setQuantity(40);
        branchProductAdd.setProductId(product.getId());
        branchProductAdd.setBranchId(branch.getId());
        branchProductService.addBranchProduct(branchProductAdd);
        List<BranchProduct> branchProductsAfter = branchProductService.getBranchProductsByBranchId(branch.getId());
        assertTrue(branchProductsAfter.stream().anyMatch(bp -> bp.getId().equals(branchProductAdd.getId())));
        int sizeAfter = branchProductsAfter.size();
        assertEquals(size + 1, sizeAfter);
        branchProductService.deleteBranchProduct(branchProductAdd.getId());
    }

    

}
