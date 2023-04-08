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
public class BranchProduct {

    private String id;
    private double quantity;
    private String productId;
    private String brandId;
    

    public BranchProduct() {
        id = UUID.randomUUID().toString();
    }

    public BranchProduct(double quantity, String productId, String brandId) {
        this();
        this.quantity = quantity;
        this.productId = productId;
        this.brandId = brandId;
    }

    public BranchProduct(String id, double quantity, String productId, String brandId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.brandId = brandId;
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
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
     * @return the brandId
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * @param brandId the brandId to set
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

   
}
