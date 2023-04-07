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
public class Employee {

    private String id;
    private String employeeName;
    private String number;
    private Date birthday;
    private Date joinDate;
    private employeeRole role;
    private String brandId;

    public enum employeeRole {
        Manager,
        Employee
    }

    public Employee() {
        this.id = UUID.randomUUID().toString();
    }

    public Employee(String employeeName, String number, Date birthday, Date joinDate, employeeRole role, String brandId) {
        this();
        this.employeeName = employeeName;
        this.number = number;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.role = role;
        this.brandId = brandId;
    }

    public Employee(String id, String employeeName, String number, Date birthday, Date joinDate, employeeRole role, String brandId) {
        this.id = id;
        this.employeeName = employeeName;
        this.number = number;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.role = role;
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
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    /**
     * @return the joinDate
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate the joinDate to set
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * @return the role
     */
    public employeeRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(employeeRole role) {
        this.role = role;
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
