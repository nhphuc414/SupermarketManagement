/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Order;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ad
 */
public interface OrderService {
    void addOrder(Order order) throws SQLException;

    void updateOrder(Order order)throws SQLException;

    void deleteOrder(String id)throws SQLException;

    Order getOrderById(String id)throws SQLException;
    
    List<Order> getOrdersByCustomerId(String customerId)throws SQLException;
    
    List<Order> getOrdersByEmployeeId(String employeeId)throws SQLException;
    
    List<Order> getOrdersByDateRange(Date startDate, Date endDate) throws SQLException;
    
    List<Order> getAllOrders()throws SQLException;
}
