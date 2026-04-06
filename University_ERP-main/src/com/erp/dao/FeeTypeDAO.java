package com.erp.dao;

import com.erp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class FeeTypeDAO {
    public void addFeeType(String feeName){
        String query = "Insert into fee_types (fee_name) values (?)";
        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,feeName);
            ps.executeUpdate();

            System.out.println("Fee type added.");

        }catch(Exception exp){
            exp.printStackTrace();
        }
    }

    public static Map<Integer,String> getAllFeeTypes(){
        Map<Integer, String> feeTypes = new HashMap<>();

        String query = "select fee_type_id, fee_name from fee_types";

        try{
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                feeTypes.put(rs.getInt("fee_type_id"),rs.getString("fee_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return feeTypes;
    }

}
