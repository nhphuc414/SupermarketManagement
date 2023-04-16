/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Branch;
import com.ktpm.services.impl.BranchServiceImpl;
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
 * @author ad
 */
public class BranchServiceTest {
      
    private static Connection conn= null;
    BranchService branchService = new BranchServiceImpl();
    
    
    public BranchServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        try {
            conn = JDBCUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BranchServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddBranch() throws Exception {
        Branch branch = new Branch();
        branch.setId("test1");
        branch.setBranchName("Test Branch");
        branch.setAddress("123 Test St");
        branchService.addBranch(branch);
        // Verify that the branch was added correctly by retrieving it from the database
        Branch retrievedBranch = branchService.getBranchById("test1");
        assertNotNull(retrievedBranch);
        assertEquals("Test Branch", retrievedBranch.getBranchName());
        assertEquals("123 Test St", retrievedBranch.getAddress());
    }

    @Test
    public void testUpdateBranch() throws Exception {
        Branch branch = new Branch();
        branch.setId("test2");
        branch.setBranchName("Test Branch");
        branch.setAddress("123 Test St");
        branchService.addBranch(branch);
        // Update the branch's information
        branch.setBranchName("Updated Branch Name");
        branch.setAddress("456 Updated St");
        branchService.updateBranch(branch);
        // Verify that the branch was updated correctly by retrieving it from the database
        Branch retrievedBranch = branchService.getBranchById("test2");
        assertNotNull(retrievedBranch);
        assertEquals("Updated Branch Name", retrievedBranch.getBranchName());
        assertEquals("456 Updated St", retrievedBranch.getAddress());
    }

    @Test
    public void testDeleteBranch() throws Exception {
        branchService.deleteBranch("test2");
         branchService.deleteBranch("test1");
        Branch retrievedBranch = branchService.getBranchById("test2");
        assertNull(retrievedBranch);
        
    }

    @Test
    public void testGetBranchById() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Branch retrievedBranch = branchService.getBranchById("1");
        assertNotNull(retrievedBranch);
        assertEquals("Trụ sở", retrievedBranch.getBranchName());
        assertEquals("06 Lê Lợi, Gò Vấp, TP. HCM", retrievedBranch.getAddress());
    }
    @Test
    public void testGetAllBranches() throws Exception {
        List<Branch> branches = branchService.getAllBranches();
        assertEquals("1", branches.get(0).getId());
        assertEquals("Trụ sở", branches.get(0).getBranchName());
    }
}
