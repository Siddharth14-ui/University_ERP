package com.erp.dao;

import com.erp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    public void markAttendance(String studentId, String courseId, String date, String status) {
        String query = "INSERT INTO attendance (student_id, course_id, date, status) VALUES (?,?,?,?)";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.setString(3, date);
            ps.setString(4, status);

            ps.executeUpdate();
            System.out.println("Attendance Marked");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAttendance() {
        String query = "SELECT * FROM attendance";

        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
