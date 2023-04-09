/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services.impl;

import com.ktpm.pojo.Branch;
import com.ktpm.services.BranchService;
import com.ktpm.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ad
 */
public class BranchServiceImpl implements BranchService {

    @Override
    public void addBranch(Branch branch) throws SQLException {
        String sql = "INSERT INTO branches (id, branch_name, address) VALUES(?,?,?)";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branch.getId());
            pstmt.setString(2, branch.getBranchName());
            pstmt.setString(3, branch.getAddress());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateBranch(Branch branch) throws SQLException {
        String sql = "UPDATE branches SET  branch_name = ?, address = ? WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branch.getBranchName());
            pstmt.setString(2, branch.getAddress());
            pstmt.setString(3, branch.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBranch(String id) throws SQLException {
        String sql = "DELETE FROM branches WHERE id = ?";
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Branch getBranchById(String id) throws SQLException {
        String sql = "SELECT * FROM branches WHERE id = ?";
        Branch branch = null;
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                branch = new Branch();
                branch.setId(rs.getString("id"));
                branch.setBranchName(rs.getString("branch_name"));
                branch.setAddress(rs.getString("address"));
            }
            return branch;
        }
    }

    @Override
    public List<Branch> getAllBranches() throws SQLException {
        String sql = "SELECT * FROM branches";
        List<Branch> branches = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConn()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Branch branch = new Branch(rs.getString("id"),
                        rs.getString("branch_name"),
                        rs.getString("Address"));
                branches.add(branch);
            }
            return branches;
        }
    }
}
