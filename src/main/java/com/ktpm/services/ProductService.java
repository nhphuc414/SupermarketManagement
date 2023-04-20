/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Product;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface ProductService {
    int addProduct(Product product) throws SQLException;

    void updateProduct(Product product) throws SQLException;

    void deleteProduct(int id) throws SQLException;

    Product getProductById(int id) throws SQLException;
    

    List<Product> getAllProducts() throws SQLException;
}