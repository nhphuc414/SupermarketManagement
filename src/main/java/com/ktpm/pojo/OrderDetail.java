/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.pojo;

import java.util.UUID;

/**
 *
 * @author ad
 */
public class OrderDetail {
    private String id;
    private String productId;
    private String orderId;
    private double quantity;
   
    public OrderDetail() {
        this.id=UUID.randomUUID().toString();
    }
    public OrderDetail(String productId, String orderId, double quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;   
    }

    public OrderDetail(String id, String productId, String orderId, double quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
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
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the quantityPerUnit
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
