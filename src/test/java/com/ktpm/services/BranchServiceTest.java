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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 *
 * @author ad
 */
public class BranchServiceTest {

    private static Connection conn = null;
    private static BranchService branchService;
    private Branch branch;

    public BranchServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.getTestConn();
        branchService = new BranchServiceImpl();
    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        branch = new Branch("test", "Test Branch Product", "Test Branch Product address");
        branchService.addBranch(branch);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        branchService.deleteBranch(branch.getId());
    }

    @Test
    public void testAddBranch() throws Exception {
        // Verify that the branch was added correctly by retrieving it from the database
        Branch retrievedBranch = branchService.getBranchById(branch.getId());
        assertNotNull(retrievedBranch);
        assertEquals(branch.getBranchName(), retrievedBranch.getBranchName());
        assertEquals(branch.getAddress(), retrievedBranch.getAddress());
        branchService.deleteBranch(branch.getId());
    }

    @Test
    public void testUpdateBranch() throws Exception {
        // Update the branch's information
        branch.setBranchName("Updated Branch Name");
        branch.setAddress("456 Updated St");
        branchService.updateBranch(branch);
        // Verify that the branch was updated correctly by retrieving it from the database
        Branch retrievedBranch = branchService.getBranchById(branch.getId());
        assertNotNull(retrievedBranch);
        assertEquals(branch.getBranchName(), retrievedBranch.getBranchName());
        assertEquals(branch.getAddress(), retrievedBranch.getAddress());
    }

    @Test
    public void testDeleteBranch() throws Exception {
        
        branchService.deleteBranch(branch.getId());
        Branch retrievedBranch = branchService.getBranchById(branch.getId());
        assertNull(retrievedBranch);
    }
    @Test
    public void testDeleteBranchFailed() throws Exception {
        BranchProductService branchProductService = new BranchProductServiceImpl();
        ProductService productService = new ProductServiceImpl();
        Product product = new Product("Test", 1000, "Test file", Product.ProductType.Quantity);
        int productId = productService.addProduct(product);
        product.setId(productId);
        
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId("add");
        branchProduct.setQuantity(40);
        branchProduct.setProductId(product.getId());
        branchProduct.setBranchId(branch.getId());
        
        branchProductService.addBranchProduct(branchProduct);
        SQLException assertThrows = assertThrows(SQLException.class, () -> {
            branchService.deleteBranch(branch.getId());
        });
        assertNotNull(assertThrows);
        branchProductService.deleteBranchProduct(branchProduct.getId());
        
        productService.deleteProduct(product.getId());
        Branch retrievedBranch = branchService.getBranchById(branch.getId());
        assertNotNull(retrievedBranch);
    }
    
    @Test
    public void testGetBranchById() throws Exception {
        Branch retrievedBranch = branchService.getBranchById(branch.getId());
        assertNotNull(retrievedBranch);
        assertEquals(branch.getBranchName(), retrievedBranch.getBranchName());
        assertEquals(branch.getAddress(), retrievedBranch.getAddress());
        branchService.deleteBranch(branch.getId());
    }

    @Test
    public void testGetAllBranches() throws Exception {
        List<Branch> branches = branchService.getAllBranches();
        assertTrue(branches.stream().anyMatch(bp -> bp.getId().equals(branch.getId())));
        int size = branches.size();
        Branch branchAdd = new Branch();
        branchAdd.setId("test1");
        branchAdd.setBranchName("Test Branch");
        branchAdd.setAddress("123 Test St");
        branchService.addBranch(branchAdd);
        List<Branch> branchesAfter = branchService.getAllBranches();
        assertTrue(branchesAfter.stream().anyMatch(bp -> bp.getId().equals(branchAdd.getId())));
        int sizeAfter = branchesAfter.size();
        assertEquals(size + 1, sizeAfter);
        branchService.deleteBranch(branchAdd.getId());
    }
}
