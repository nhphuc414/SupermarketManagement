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
public class Product {

    private int id;
    private String productName;
    private double price;
    private String origin;
    private ProductType productType;

    public enum ProductType {
        Quantity,
        Weight
    }

    public Product() {
    }

    public Product(String productName, double price, String origin, ProductType productType) {
        this.productName = productName;
        this.price = price;
        this.origin = origin;
        this.productType = productType;
    }

    public Product(int id, String productName, double price, String origin, ProductType productType) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.origin = origin;
        this.productType = productType;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the productType
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    @Override
    public String toString() {
        String quantity = this.getProductType()==ProductType.Quantity ?"Đơn vị":"Kg";
        return id + " " + productName + " (" + origin + ")" + " (" + quantity + ")" ;
    }
}
