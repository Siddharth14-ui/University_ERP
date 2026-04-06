package com.erp.dao;

import com.erp.util.DBUtil;
import com.erp.model.FeeReportUI;

import java.sql.ResultSet;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAO {
//    public void addPayment(int studentFeeId , double amount){
//        String query = "insert into payments(student_fee_id, amount_paid, is_completed) values(?,?,?)";
//
//        try{
//            Connection con = DBUtil.getConnection();
//            PreparedStatement ps = con.prepareStatement(query);
//
//            ps.setInt(1,studentFeeId);
//            ps.setDouble(2,amount);
//            ps.setBoolean(3,true);
//
//            ps.executeUpdate();
//
//            System.out.println("Payment added successfully.");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        }

    public void addPayment(int feeId, double amount) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psInsert = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // 🔹 1. Get total fee and already paid
            String checkQuery = """
            SELECT 
                sf.amount AS total_fee,
                COALESCE(SUM(p.amount_paid), 0) AS paid_amount
            FROM student_fees sf
            LEFT JOIN payments p ON sf.student_fee_id = p.student_fee_id
            WHERE sf.student_fee_id = ?
            GROUP BY sf.student_fee_id
        """;

            psCheck = conn.prepareStatement(checkQuery);
            psCheck.setInt(1, feeId);
            rs = psCheck.executeQuery();

            double totalFee;
            double paidAmount;
            if (rs.next()) {
                totalFee = rs.getDouble("total_fee");
                paidAmount = rs.getDouble("paid_amount");

//                double newTotalPaid = paidAmount + amount;
//
//                if (newTotalPaid > totalFee) {
//                    throw new RuntimeException("Payment exceeds total fee!");
//                }
            } else {
                throw new RuntimeException("Invalid Fee ID!");
            }

            // 🔹 2. Insert payment if valid
            String insertQuery = """
            INSERT INTO payments (student_fee_id, amount_paid, is_completed)
            VALUES (?, ?, ?)
        """;

            psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, feeId);
            psInsert.setDouble(2, amount);

            boolean isCompleted = (paidAmount + amount) == totalFee;
            psInsert.setBoolean(3, isCompleted);

            psInsert.executeUpdate();

            System.out.println("Payment added successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}



