/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Branch;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ad
 */
public interface BranchService {

    public void addBranch(Branch branch) throws SQLException;

    public void updateBranch(Branch branch) throws SQLException;

    public void deleteBranch(Branch branch) throws SQLException;

    public Branch getBranchById(String id) throws SQLException;

    public List<Branch> getAllBranches() throws SQLException;
}
