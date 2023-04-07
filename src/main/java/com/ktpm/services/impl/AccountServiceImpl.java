/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Account;
import com.ktpm.services.AccountService;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ad
 */
public class AccountServiceImpl implements AccountService {

    @Override
    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (id, username, password, display_name, account_type, employee_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account.getId());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPassword());
            pstmt.setString(4, account.getDisplayName());
            pstmt.setString(5, account.getAccountType().toString());
            pstmt.setString(6, account.getEmployeeId());
            pstmt.executeUpdate();
        }
    }
    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET username = ?, password = ?, display_name = ?, account_type = ?, employee_id = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getDisplayName());
            pstmt.setString(4, account.getAccountType().toString());
            pstmt.setString(5, account.getEmployeeId());
            pstmt.setString(6, account.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteAccount(Account account) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Account getAccountByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                Account account = null;
                if (rs.next()) {
                    account = new Account();
                    account.setId(rs.getString("id"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    account.setDisplayName(rs.getString("display_name"));
                    account.setAccountType(Account.AccountType.valueOf(rs.getString("account_type")));
                    account.setEmployeeId(rs.getString("employee_id"));
                }
                return account;
            }
        }
    }

    @Override
    public List<Account> getAllAccounts() throws SQLException {
        String sql = "SELECT * FROM accounts";
        try (Connection conn = JDBCUtils.getConn(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getString("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setDisplayName(rs.getString("display_name"));
                account.setAccountType(Account.AccountType.valueOf(rs.getString("account_type")));
                account.setEmployeeId(rs.getString("employee_id"));
                accounts.add(account);
            }
            return accounts;
        }
    }
}
