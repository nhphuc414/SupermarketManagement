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
    private int productId;
    private String branchId;
    

    public BranchProduct() {
        id = UUID.randomUUID().toString();
    }

    public BranchProduct(double quantity, int productId, String branchId) {
        this();
        this.quantity = quantity;
        this.productId = productId;
        this.branchId = branchId;
    }

    public BranchProduct(String id, double quantity, int productId, String branchId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.branchId = branchId;
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
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the branchId
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

   
}
