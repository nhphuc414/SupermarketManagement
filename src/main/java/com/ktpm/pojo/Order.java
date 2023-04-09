/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.pojo;

import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author ad
 */
public class Order {

    private String id;
    private Date orderDate;
    private double cashReceived;
    private String employeeId;
    private String customerId;

    public Order() {
        id = UUID.randomUUID().toString();
    }

    public Order(Date orderDate, double cashReceived, String employeeId, String customerId) {
        this();
        this.orderDate = orderDate;
        this.cashReceived = cashReceived;
        this.employeeId = employeeId;
        this.customerId = customerId;
    }

    public Order(String id, Date orderDate, double cashReceived, String employeeId, String customerId) {
        this.id = id;
        this.orderDate = orderDate;
        this.cashReceived = cashReceived;
        this.employeeId = employeeId;
        this.customerId = customerId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the cashReceived
     */
    public double getCashReceived() {
        return cashReceived;
    }

    /**
     * @param cashReceived the cashReceived to set
     */
    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
}
