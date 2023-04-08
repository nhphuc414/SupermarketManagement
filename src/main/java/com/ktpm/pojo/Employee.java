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
public class Employee {

    private String id;
    private String employeeName;
    private String number;
    private Date birthday;
    private Date joinDate;
    private String username;
    private String password;
    private EmployeeRole employeeRole;
    private String branchId;

    public enum EmployeeRole {
        Manager,
        Employee
    }

    public Employee() {
        this.id=UUID.randomUUID().toString();
    }

    public Employee(String employeeName, String number, Date birthday, Date joinDate, String username, String password, EmployeeRole employeeRole, String branchId) {
        this();
        this.employeeName = employeeName;
        this.number = number;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.username = username;
        this.password = password;
        this.employeeRole = employeeRole;
        this.branchId = branchId;
    }
    
    public Employee(String id, String employeeName, String number, Date birthday, Date joinDate, String username, String password, EmployeeRole employeeRole, String branchId) {
        this.id = id;
        this.employeeName = employeeName;
        this.number = number;
        this.birthday = birthday;
        this.joinDate = joinDate;
        this.username = username;
        this.password = password;
        this.employeeRole = employeeRole;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the employeeRole
     */
    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    /**
     * @param employeeRole the employeeRole to set
     */
    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
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
