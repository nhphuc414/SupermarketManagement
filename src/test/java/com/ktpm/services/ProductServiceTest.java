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

    private static Connection conn;
    private static ProductService productService;
    private Product product;

    public ProductServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {

        conn = JDBCUtils.getConn();
        productService = new ProductServiceImpl();
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
    public void setUp() throws SQLException {
        product = new Product();
        product.setProductName("Test Product");
        product.setPrice(100.0);
        product.setOrigin("Test Origin");
        product.setProductType(Product.ProductType.Quantity);
        int productId = productService.addProduct(product);
        product.setId(productId);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        productService.deleteProduct(product.getId());
    }

    @Test
    public void testAddProduct() throws SQLException {
        
        // Thêm sản phẩm vào CSDL
        Product existProduct = productService.getProductById(product.getId());
        assertNotNull(existProduct);
        assertEquals(product.getId(), existProduct.getId());
        assertEquals(product.getProductName(), existProduct.getProductName());
        assertEquals(product.getOrigin(), existProduct.getOrigin());
        assertEquals(product.getProductType(), existProduct.getProductType());
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        product.setPrice(12.99);
        product.setOrigin("Test Update");
        product.setProductName("Test Update");
        product.setProductType(Weight);
        productService.updateProduct(product);
        // retrieve the updated product from the database
        Product updatedProduct = productService.getProductById(product.getId());
        // verify that the product was updated correctly
        assertEquals(product.getProductName(), updatedProduct.getProductName());
        assertEquals(product.getProductType(), updatedProduct.getProductType());
        assertEquals(product.getOrigin(), updatedProduct.getOrigin());
        assertEquals(product.getPrice(), updatedProduct.getPrice(), 0.001);
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        
        productService.deleteProduct(product.getId());
        // Retrieve the deleted product
        Product deletedProduct = productService.getProductById(product.getId());
        // Verify that the deleted product is null
        assertNull(deletedProduct);
    }

    @Test
    public void testGetProductById() throws Exception {
        // Retrieve the branch from the database and verify that it was retrieved correctly
        Product retrievedProduct = productService.getProductById(product.getId());
        assertNotNull(retrievedProduct);
        assertEquals(product.getProductName(), retrievedProduct.getProductName());
        assertEquals(product.getOrigin(), retrievedProduct.getOrigin());
        assertEquals(product.getPrice(), retrievedProduct.getPrice(), 0.001);
        assertEquals(product.getProductType(), retrievedProduct.getProductType());
    }

    @Test
    public void testGetAllProduct() throws SQLException {
        List<Product> products = productService.getAllProducts();
        assertTrue(products.stream().anyMatch(p -> p.getId() == product.getId()));
        int size = products.size();
        //add new product
        Product productAdd = new Product();
        productAdd.setProductName("Test Product Add");
        productAdd.setPrice(100.0);
        productAdd.setOrigin("Test Origin Add");
        productAdd.setProductType(Product.ProductType.Quantity);
        int productIdAdd = productService.addProduct(productAdd);
        productAdd.setId(productIdAdd);
        //list products after 
        List<Product> productsAfter = productService.getAllProducts();
        assertTrue(productsAfter.stream().anyMatch(p -> p.getId() == product.getId()));
        int sizeAfter = productsAfter.size();
        
        assertEquals(size + 1, sizeAfter);
        productService.deleteProduct(productAdd.getId());

    }

}
