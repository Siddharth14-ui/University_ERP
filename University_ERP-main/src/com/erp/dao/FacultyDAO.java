package com.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.erp.model.Faculty;
import com.erp.util.DBUtil;

public class FacultyDAO {
    public void addFaculty(Faculty f){
        String query = "insert ignore into faculty(faculty_id, name, email, department) values(?,?,?,?)";
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, f.getFacutlyId());
            ps.setString(2,f.getName());
            ps.setString(3,f.getEmail());
            ps.setString(4,f.getDepartment());

            ps.executeUpdate();
            System.out.println("Faculty Added.");
            
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
