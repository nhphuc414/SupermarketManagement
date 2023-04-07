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
public class Account {

    private String id;
    private String username;
    private String password;
    private String displayName;
    private AccountType accountType;
    private String employeeId;

    public enum AccountType {
        Admin,
        Employee
    }
    
    public Account(String id, String username, String password, String displayName, AccountType accountType, String employeeId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.accountType = accountType;
        this.employeeId = employeeId;
    }

    
    public Account(String username, String password, String displayName, AccountType accountType, String employeeId) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.accountType = accountType;
        this.employeeId = employeeId;
    }
    

    public Account() {
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
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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
