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
public class Customer {

    private String id;
    private String customerName;
    private String number;
    private Date birthday;

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }

    public Customer(String customerName, String number, Date birthday) {
        this();
        this.customerName = customerName;
        this.number = number;
        this.birthday = birthday;
    }

    public Customer(String id, String customerName, String number, Date birthday) {
        this.id = id;
        this.customerName = customerName;
        this.number = number;
        this.birthday = birthday;
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
