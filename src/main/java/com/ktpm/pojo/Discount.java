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
public class Discount {

    private int id;
    private Date startDate;
    private Date endDate;
    private double discountPercent;
    private int productId;

    public Discount() {
    }

    public Discount(Date startDate, Date endDate, double discountPercent, int productId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercent = discountPercent;
        this.productId = productId;
    }

    public Discount(int id, Date startDate, Date endDate, double discountPercent, int productId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercent = discountPercent;
        this.productId = productId;
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the discountPercent
     */
    public double getDiscountPercent() {
        return discountPercent;
    }

    /**
     * @param discountPercent the discountPercent to set
     */
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
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

}
