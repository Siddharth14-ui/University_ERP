package com.erp.model;

public class StudentFees {
    private int studentFeeId;
    private String studentId;
    private String semester;
    private int feeTypeId;
    private String referenceId;
    private double amount;

    public int getStudentFeeId(){
        return studentFeeId;
    }
    public void setStudentFeeId(int studentFeeId){
        this.studentFeeId = studentFeeId;
    }

    public String getStudentId(){
        return studentId;
    }
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }

    public String getSemester(){
        return semester;
    }
    public void setSemester(String semester){
        this.semester = semester;
    }

    public int getFeeTypeId(){
        return feeTypeId;
    }
    public void setFeeTypeId(int feeTypeId){
        this.feeTypeId = feeTypeId;
    }

    public String getReferenceId(){
        return referenceId;
    }
    public void setReferenceId(String referenceId){
        this.referenceId = referenceId;
    }

    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
}
