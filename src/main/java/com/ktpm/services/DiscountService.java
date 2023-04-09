/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Discount;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface DiscountService {
    void addDiscount(Discount discount) throws SQLException;

    void updateDiscount(Discount discount) throws SQLException;

    void deleteDiscount(int id) throws SQLException;

    Discount getDiscountById(int id) throws SQLException;
    
    List<Discount> getDiscountsByProductId(int productId) throws SQLException;
     
    List<Discount> getAllDiscounts()throws SQLException;
}
