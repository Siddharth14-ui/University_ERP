package com.erp.main;

import com.erp.dao.*;
import com.erp.model.*;

import java.util.*;

public class TestApp{
    public static void main (String [] args){
        Course course = new Course("CSE3001","Data Structures",4);

        CourseDAO dao = new CourseDAO();
        dao.addCourse(course);
        dao.addCourse(new Course("CSE4019","Advanced Java Programming", 4));
        dao.getAllCourses();
        dao.updateCourse("CSE4019","CSE4019","Advanced Java Programming",3);
        dao.getAllCourses();
        dao.addCourse(new Course("MAT1001","MATHS CALC", 4));
        dao.deleteCourse("MAT1001");
        dao.getAllCourses();

        StudentDAO sdao = new StudentDAO();
        sdao.addStudent(new Student("23BCE10426", "Prashant Bhardwaj", "prashant@gmail.com", "123456789"));
        sdao.addStudent(new Student("23BCE11819", "Georgia Moon","georgia@gmail.com", "223456789"));
        sdao.updateStudent("23BCE11819","23BCE11819","Georgia Grey","gerogia@gmail.com","2219123456");
        sdao.getAllStudents();

        EnrollmentDAO edao = new EnrollmentDAO();
        edao.enrollStudent(new Enrollment("23BCE10426","CSE4019","Winter Semester 2025-26"));
        edao.enrollStudent(new Enrollment("23BCE10426","CSE3001","Fall Semester 2025-26"));
        edao.enrollStudent(new Enrollment("23BCE11819","CSE4019","Winter Semester 2025-26"));

        edao.getFullEnrollmentDetails("23BCE10426");
        edao.getStudentCourses("23BCE11819");



        StudentFeesDAO SFdao = new StudentFeesDAO();
        SFdao.addStudentFees("23BCE10426","Winter Semester 2025-26",1,null,59000);
        SFdao.addStudentFees("23BCE10426","Winter Semester 2025-26",2, "CSE4019",3000 );
        SFdao.addStudentFees("23BCE10426","Winter Semester 2025-26",2, "CSE3001",3000 );

//        PaymentDAO pDAO = new PaymentDAO();
//        pDAO.addPayment(1,20000);
//        pDAO.addPayment(2,1500);
//        pDAO.addPayment(3,2000);

        FeesReportDAO frdao = new FeesReportDAO();
        List<FeeReport> reports = frdao.getFullFeesReport();

        if (reports.isEmpty()) {
            System.out.println("No data found.");
            return;
        }
        System.out.println("Total records: "+reports.size());

        for (FeeReport fr : reports) {
            System.out.println("----------------------------");
            System.out.println("Fee ID: "+fr.getFeeId());
            System.out.println("Student ID  : " + fr.getStudentId());
            System.out.println("Name        : " + fr.getStudentName());
            System.out.println("Semester    : " + fr.getSemester());
            System.out.println("Fee Type    : " + fr.getFeeName());
            System.out.println("Course      : " + fr.getCourseName());
            System.out.println("Total Fee   : " + fr.getTotalFee());
            System.out.println("Paid Amount : " + fr.getPaidAmount());
            System.out.println("Due Amount  : " + fr.getDueAmount());
        }
    }

        // inserting student fees:
//        StudentFeesDAO sfDAO = new StudentFeesDAO();
//        sfDAO.addStudentFees("23BCE10426","Winter Semester 2025-26",1, null, 60000 );
//        sfDAO.addStudentFees("23BCE10426","Fall Semester 2025-26",2,"CSE4019",3000);
//        sfDAO.addStudentFees("23BCE11819","Winter Semester 2025-26",2,"CSE4019",3000);
//
//
//        FeesReportDAO feeRdao =new FeesReportDAO();
//        List<FeeReport> reports = feeRdao.getFullFeesReport();
//
//        if(reports.isEmpty()){
//            System.out.println("No data found.");
//        }
//        for (FeeReport fr : reports){
//            System.out.println(":::::::::::::::::::::::::::::::::::::::::");
//            System.out.println("Student ID : "+fr.getStudentId());
//            System.out.println("Student Name : "+fr.getStudentName());
//            System.out.println("Semester : "+fr.getSemester());
//            System.out.println("Fee Type : "+fr.getFeeName());
//            System.out.println("Course : "+fr.getCourseName());
//            System.out.println("Total Fee : "+fr.getTotalFee());
//            System.out.println("Paid Amount : "+fr.getPaidAmount());
//            System.out.println("Due Amount : "+fr.getDueAmount());
//        }


    }

