/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Customer;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface CustomerService {
    void addCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer customer)throws SQLException;

    void deleteCustomer(String id) throws SQLException;

    Customer getCustomerById(String id) throws SQLException;
    
    Customer getCustomerByNumber(String number) throws SQLException;

    List<Customer> getAllCustomers() throws SQLException;
}
