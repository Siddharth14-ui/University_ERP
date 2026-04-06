package com.erp.model;

public class Enrollment {
    private String studentId;
    private String courseId;
    private String semester;

    public Enrollment(String studentId, String courseId, String semester){
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
    }
    public String getCourseId(){
        return courseId;
    }
    public String getStudentId(){
        return studentId;
    }
    public String getSemester(){
        return semester;
    }
}
