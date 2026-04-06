package com.erp.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.erp.dao.StudentFeesDAO;
import com.erp.dao.FeeTypeDAO;
import com.erp.dao.FeesReportDAO;
import com.erp.dao.StudentDAO;
import com.erp.model.FeeReport;
import com.erp.util.DBUtil;


public class FeesReportDAO {

    public List<FeeReport> getFullFeesReport() {
        List<FeeReport> list = new ArrayList<>();

        String query = """
                SELECT\s
                    sf.student_fee_id,
                    s.student_id,
                    s.name AS student_name,
                    sf.semester,
                    ft.fee_name,
                    c.course_name,
                    sf.amount AS total_fee,
                
                    COALESCE(SUM(p.amount_paid), 0) AS paid_amount,
                
                    GREATEST(sf.amount - COALESCE(SUM(p.amount_paid), 0), 0) AS due_amount
                
                FROM student_fees sf
                
                JOIN students s\s
                    ON sf.student_id = s.student_id
                
                JOIN fee_types ft\s
                    ON sf.fee_type_id = ft.fee_type_id
                
                LEFT JOIN courses c\s
                    ON sf.reference_id = c.course_id
                
                LEFT JOIN payments p\s
                    ON sf.student_fee_id = p.student_fee_id
                
                GROUP BY sf.student_fee_id,
                    s.student_id,
                    s.name,
                    sf.semester,
                    ft.fee_name,
                    c.course_name,
                    sf.amount
                            """;
        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                FeeReport fr = new FeeReport();

                int id = rs.getInt("student_fee_id");
                System.out.println("DAO Fee ID: "+id);
                fr.setStudentFeeId(id);

                fr.setFeeId(rs.getInt("student_fee_id"));
                fr.setStudentId(rs.getString("student_id"));
                fr.setStudentName(rs.getString("student_name"));
                fr.setSemester(rs.getString("semester"));
                fr.setFeeName(rs.getString("fee_name"));
                fr.setCourseName(rs.getString("course_name"));
                fr.setTotalFee(rs.getDouble("total_fee"));
                fr.setPaidAmount(rs.getDouble("paid_amount"));
                fr.setDueAmount(rs.getDouble("due_amount"));

                System.out.println(rs.getMetaData().getColumnCount());

                list.add(fr);
            }

        }catch(Exception exp){
            exp.printStackTrace();
        }
        return list;
    }

    public List<FeeReport> searchByStudentId(String keyword){
        List<FeeReport> list = new ArrayList<>();

        String query = """
                Select 
                    sf.student_fee_id,
                    s.student_id,
                    s.name,
                    sf.semester,
                    ft.fee_name,
                    c.course_name,
                    sf.amount as total_fee,
                    
                    COALESCE(SUM(
                                    CASE
                                        WHEN p.is_completed = TRUE THEN p.amount_paid
                                        ELSE 0
                                    END
                                ), 0) AS paid_amount
                    from student_fees sf
                    
                    Join students s on s.student_id = sf.student_id
                    
                    Join fee_types ft on ft.fee_type_id = sf.fee_type_id
                    
                    Left Join courses c on sf.reference_id = c.course_id
                    
                    Left Join payments p on sf.student_fee_id = p.student_fee_id
                    
                    Where s.student_id Like ?
                    
                    Group BY
                                 sf.student_fee_id,
                                s.student_id,
                                s.name,
                                sf.semester,
                                ft.fee_name,
                                c.course_name,
                                sf.amount
                    
                """;

            try {
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1,"%"+keyword+"%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    FeeReport fr = new FeeReport();
                    fr.setStudentFeeId(rs.getInt("student_fee_id"));
                    fr.setStudentId(rs.getString("student_id"));
                    fr.setStudentName(rs.getString("name"));
                    fr.setSemester(rs.getString("semester"));
                    fr.setFeeName(rs.getString("fee_name"));
                    fr.setCourseName(rs.getString("course_name"));
                    fr.setTotalFee(rs.getDouble("total_fee"));

                    double paid = rs.getDouble("paid_amount");
                    fr.setPaidAmount(paid);
                    fr.setDueAmount(fr.getTotalFee() - paid);

                    list.add(fr);
                }
                }catch(Exception e) {
                e.printStackTrace();
            }return list;
    }
}

