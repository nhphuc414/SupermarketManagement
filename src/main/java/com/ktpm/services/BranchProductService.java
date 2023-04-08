/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.BranchProduct;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface BranchProductService {

    void addBranchProduct(BranchProduct branchProduct) throws SQLException;

    void updateBranchProduct(BranchProduct branchProduct) throws SQLException;

    void deleteBranchProduct(String id) throws SQLException;

    BranchProduct getBranchProductById(String id) throws SQLException;
    
    List<BranchProduct> getBranchProductsByProductId(String productId) throws SQLException;

    List<BranchProduct> getBranchProductsByBranchId(String branchId) throws SQLException;

    List<BranchProduct> getBranchProductsByBranchIdAndProductId(String branchId, String productId) throws SQLException;

    List<BranchProduct> getAllBranchProducts() throws SQLException;

}
