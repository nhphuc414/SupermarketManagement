/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.pojo;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author ad
 */
public class Order {

    private String id;
    private Date orderDate;
    private double cash_received;
    private String customerId;
    private String employeeId;

    public Order() {
        id = UUID.randomUUID().toString();
    }

    public Order(Date orderDate, double cash_received, String customerId, String employeeId) {
        this();
        this.orderDate = orderDate;
        this.cash_received = cash_received;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    public Order(String id, Date orderDate, double cash_received, String customerId, String employeeId) {
        this.id = id;
        this.orderDate = orderDate;
        this.cash_received = cash_received;
        this.customerId = customerId;
        this.employeeId = employeeId;
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
     * @return the cash_received
     */
    public double getCash_received() {
        return cash_received;
    }

    /**
     * @param cash_received the cash_received to set
     */
    public void setCash_received(double cash_received) {
        this.cash_received = cash_received;
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

}
