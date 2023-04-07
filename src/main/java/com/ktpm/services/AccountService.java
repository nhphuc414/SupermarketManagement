/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Account;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface AccountService {

    public void addAccount(Account account) throws SQLException;

    public void updateAccount(Account account) throws SQLException;

    public void deleteAccount(Account account) throws SQLException;

    public Account getAccountByUsernameAndPassword(String username, String password) throws SQLException;

    public List<Account> getAllAccounts() throws SQLException;
}
