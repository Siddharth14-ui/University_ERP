package com.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.erp.model.Enrollment;
import com.erp.util.DBUtil;

public class EnrollmentDAO {

    //enroll student..
    public void enrollStudent(Enrollment e){
        String query = "Insert Ignore into enrollments (student_id, course_id, semester) values(?,?,?)";

        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,e.getStudentId());
            ps.setString(2,e.getCourseId());
            ps.setString(3, e.getSemester());

            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Enrollment Successful.");
            }else{
                System.out.println("User Already Enrolled.");
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    //view enrollments
//    public void getAllEnrollments(){
//        String query = "Select * from enrollments";
//
//        try{
//            Connection con = DBUtil.getConnection();
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                System.out.println(rs.getString("student_id")+ " | " + rs.getString("course_id")+ " | " + rs.getString("semester"));
//            }
//
//        }catch(Exception e ){
//            e.printStackTrace();
//        }
//
//    }

//    public void getCoursesByStudent(String studentId){
//        String query = "Select * from enrollments where student_id=?";
//
//        try{
//            Connection con = DBUtil.getConnection();
//            PreparedStatement ps = con.prepareStatement(query);
//            ps.setString(1,studentId);
//
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                System.out.println(rs.getString("course_id")+" | "+ rs.getString("semester"));
//            }
//
//        }catch(Exception exc){
//            exc.printStackTrace();
//        }
//
//    }

    public void getFullEnrollmentDetails(String studentId){
        String query = """
                Select s.name, s.student_id, c.course_name, c.course_id, e.semester
                from enrollments e
                join students s on e.student_id = s.student_id
                join courses c on e.course_id = c.course_id
                """;
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("name")+"("+rs.getString("student_id")+")"+" | "+rs.getString("course_name")+"("+rs.getString("course_id")+")"+" | "+rs.getString("semester"));
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void getStudentCourses(String studentId){
        String query = """
                select c.course_name, c.course_id, e.semester
                from enrollments e
                join courses c on e.course_id = c.course_id
                where e.student_id=?
                """;
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("course_name") +"("+rs.getString("course_id")+")"+ " | " +rs.getString("semester"));
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}
