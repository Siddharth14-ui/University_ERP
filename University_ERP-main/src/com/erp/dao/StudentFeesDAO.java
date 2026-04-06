package com.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.erp.util.DBUtil;

public class StudentFeesDAO {

    //assigning new payment to student..
    public void addStudentFees(String studentId, String semester, int feeTypeId, String referenceId, double amount ) {

        try {
            String checkType = "Select requires_reference from fee_types where fee_type_id = ?";
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(checkType);
            ps.setInt(1, feeTypeId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid fee type.");
            }
            boolean requiresReference = rs.getBoolean("requires_reference");
            if (requiresReference) {
                if (referenceId == null) {
                    System.out.println("Course ID required.");
                    return;
                }
                String checkCourse = "Select * from courses where course_id = ?";
                PreparedStatement ps2 = con.prepareStatement(checkCourse);
                ps2.setString(1, referenceId);

                ResultSet rs2 = ps.executeQuery();
                if (!rs2.next()) {
                    System.out.println("Invalid course ID");
                    return;
                }
            } else {
                referenceId = null;
            }

            String insert = "insert ignore into student_fees(student_id, semester, fee_type_id, reference_id, amount) values(?,?,?,?,?)";
            PreparedStatement ps3 = con.prepareStatement(insert);

            ps3.setString(1, studentId);
            ps3.setString(2, semester);
            ps3.setInt(3, feeTypeId);

            if (referenceId == null) {
                ps3.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps3.setString(4, referenceId);
            }
            ps3.setDouble(5, amount);
            System.out.println("DEBUG → " + studentId + " | " + semester + " | " + feeTypeId + " | " + referenceId + " | " + amount);
            ps3.executeUpdate();
            System.out.println("Fee added successfully.");

        } catch (SQLException exp) {
            exp.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // wrong payment deletion..
    public void deleteWrongPayment(int paymentID){
        String query = "delete from payments where payment_id=?";
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,paymentID);

            int rows = ps.executeUpdate();

            if(rows>0){
                System.out.println("Payment deleted.");
            }else{
                System.out.println("Payment not found");
            }

        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    //payment status (True/Flase for payment completion or not)
    public void paymentCompletion(int paymentId){
        String query = "update payments set is_completed = True where payment_id = ?";
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,paymentId);

            int rows = ps.executeUpdate();
            if(rows>0){
                System.out.println("Payment status changed.");
            }else{
                System.out.println("Payment not found.");
            }

        }catch (Exception exp){
            exp.printStackTrace();
        }
    }
}
