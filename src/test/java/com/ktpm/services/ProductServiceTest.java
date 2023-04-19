/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Product;
import static com.ktpm.pojo.Product.ProductType.Weight;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class ProductServiceTest {
    private static Connection conn = null;
    ProductService productService = new ProductServiceImpl();
    
    
    public ProductServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        try {
            conn = JDBCUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @AfterAll
    public static void tearDownClass() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductServiceTest.class.getName()).log(Level.SEVERE, null, ex);
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
//     public void testAddProduct() throws SQLException {
//        // Tạo một sản phẩm mới
//        Product product = new Product();
//        product.setProductName("Test Product");
//        product.setPrice(100.0);
//        product.setOrigin("Test Origin");
//        product.setProductType(Weight);
//
//        // Thêm sản phẩm vào CSDL
//        int productId = productService.addProduct(product);
//
//        // Truy vấn CSDL để lấy thông tin sản phẩm mới được thêm vào
//        String sql = "SELECT * FROM products WHERE id = ?";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setInt(1, productId);
//        ResultSet rs = pstmt.executeQuery();
//
//        // Kiểm tra xem sản phẩm mới được thêm vào có đúng với thông tin của đối tượng ban đầu hay không
//        assertTrue(rs.next());
//        assertEquals(product.getProductName(), rs.getString("product_name"));
//        assertEquals(product.getPrice(), rs.getDouble("price"), 0.0);
//        assertEquals(product.getOrigin(), rs.getString("origin"));
//        assertEquals(product.getProductType().toString(), rs.getString("product_type"));
//    }
//    @Test
//     public void testUpdateProduct() throws SQLException {
//        // create a new product
//        Product product = new Product("Test Product", 9.99, "Test Origin", Weight);
//        int productId = productService.addProduct(product);
//
//        // update the product
//        product.setId(productId);
//        product.setPrice(12.99);
//        product.setProductType(Weight);
//        productService.updateProduct(product);
//
//        // retrieve the updated product from the database
//        Product updatedProduct = productService.getProductById(productId);
//
//        // verify that the product was updated correctly
//        assertEquals("Test Product", updatedProduct.getProductName());
//        assertEquals(12.99, updatedProduct.getPrice(), 0.001);
//        assertEquals("Test Origin", updatedProduct.getOrigin());
//        assertEquals(Weight, updatedProduct.getProductType());
//    }
//    @Test
//    public void testDeleteProduct() throws SQLException {
//    // Create a new product to be deleted
//    Product product = new Product("Test Product", 10.99, "Test Origin", Weight);
//    int productId = productService.addProduct(product);
//    
//    // Delete the product
//    productService.deleteProduct(productId);
//    
//    // Retrieve the deleted product
//    Product deletedProduct = productService.getProductById(productId);
//    
//    // Verify that the deleted product is null
//    assertNull(deletedProduct);
//    }
//    @Test
//    public void testGetProductById() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        Product retrievedProduct = productService.getProductById(1);
//        assertNotNull(retrievedProduct);
//        assertEquals("Táo", retrievedProduct.getProductName());
//        assertEquals(10000.0, retrievedProduct.getPrice());
//        assertEquals("Việt Nam", retrievedProduct.getOrigin());
//        assertEquals(Weight, retrievedProduct.getProductType());
//        
//    }
//    @Test
//    public void testGetProductByName() throws Exception {
//        // Retrieve the branch from the database and verify that it was retrieved correctly
//        Product retrievedProduct = productService.getProductByName("Táo");
//        assertNotNull(retrievedProduct);
//        assertEquals(1, retrievedProduct.getId());
//        assertEquals(10000.0, retrievedProduct.getPrice());
//        assertEquals("Việt Nam", retrievedProduct.getOrigin());
//        assertEquals(Weight, retrievedProduct.getProductType());
//    }
//    @Test
//    public void testGetAllProduct() throws SQLException {
//        // Call the method being tested
//        List<Product> results = productService.getAllProducts();
//        // Assert that the correct number of results were returned
//        int sl = results.size();
//        productService.addProduct(new Product(9, "Sữa", 10000, "Việt Nam", Weight));
//        assertEquals(sl , results.size());
//
//    }
//    
}
