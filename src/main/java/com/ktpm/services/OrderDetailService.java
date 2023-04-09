/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.OrderDetail;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface OrderDetailService {

    void addOrderDetail(OrderDetail orderDetail) throws SQLException;

    void updateOrderDetail(OrderDetail orderDetail) throws SQLException;

    void deleteOrderDetail(String id) throws SQLException;

    OrderDetail getOrderDetailById(String id) throws SQLException;

    List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws SQLException;
    
    List<OrderDetail> getOrderDetailsByProductId(int productId) throws SQLException;

    List<OrderDetail> getAllOrderDetails() throws SQLException;

}
