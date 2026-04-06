package com.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.erp.model.Student;
import com.erp.util.DBUtil;

import javax.swing.*;

public class StudentDAO {

    //add Student info..
    public void addStudent(Student s) {
        String query = "Insert Ignore into students (student_id, name, email, phone) values (?,?,?,?)";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student Added.");
            } else {
                System.out.println("Student entry already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //delete student from students record..
    public void deleteStudent(String studentId) {
        String query = "delete from students where student_id=?";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);

            ps.executeUpdate();
            System.out.println("Student Deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    //show student entry..
    public void getAllStudents() {
        String query = "Select * from students";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("student_id") + " | " + rs.getString("name") + " | " + rs.getString("email") + " | " + rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update student info..
    public void updateStudent(String studentId, String newStudentId, String newStudentName, String newStudentEmail, String newStudentPhone) {
        String query = "Update students set student_id=?, name=?, email=?, phone=? where student_id=?";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newStudentId);
            ps.setString(2, newStudentName);
            ps.setString(3, newStudentEmail);
            ps.setString(4, newStudentPhone);
            ps.setString(5, studentId);

            ps.executeUpdate();
            System.out.println("Student Info Updated Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

