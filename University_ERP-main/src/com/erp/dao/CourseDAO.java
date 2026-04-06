package com.erp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.erp.model.Course;
import com.erp.util.DBUtil;

public class CourseDAO {
    //for adding a course..
    public void addCourse(Course course){
        String query = "Insert into courses (course_id, course_name, credits) values (?,?,?);";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, course.getCourseId());
            ps.setString(2,course.getCourseName());
            ps.setInt(3,course.getCredits());

            ps.executeUpdate();

            System.out.println("Courses Added Successfully");
        }catch(Exception e){
            if(e.getMessage().contains("Duplicate")){
                System.out.println("Course entry already exists.");
            }else{
                e.printStackTrace();
            }
        }
    }

    // for printing course info..
    public static Map<String,String> getAllCourses(){
        Map<String,String> courses = new HashMap<>();

        String query = "Select course_id, course_name from courses";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
//                System.out.println(
//                        rs.getString("course_id")+ " | " + rs.getString("course_name")+ " | "+ rs.getInt("credits"));
                courses.put(
                        rs.getString("course_id"),rs.getString("course_name")
                );

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return courses;
    }

    //for updating course info..
    public void updateCourse(String newCourseId, String courseId, String newCourseName, int newCredits){
        String query = "Update courses set course_id=?, course_name=?, credits=? where course_id=?";
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,newCourseId);
            ps.setString(2,newCourseName);
            ps.setInt(3,newCredits);
            ps.setString(4,courseId);

            ps.executeUpdate();
            System.out.println("Course Updated");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //delete courses from courses table..
    public void deleteCourse(String courseId){
        String query = "delete from courses where course_id=?";

        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseId);

            ps.executeUpdate();
            System.out.println("Course Deleted.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
