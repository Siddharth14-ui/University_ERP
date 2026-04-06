package com.erp.model;

public class FeeReport {
    private int studentFeeId;
    private String studentName;
    private String studentId;
    private String semester;
    private String feeName;
    private String courseName; // nullable
    private double totalFee;
    private double paidAmount;
    private double dueAmount;
    private int feeId;

    public void setFeeId(int feeId){
        this.studentFeeId = feeId;
    }
    public int getFeeId(){return studentFeeId;}

    public String getStudentName(){
        return studentName;
    }
    public void setStudentName(String studentName){
        this.studentName = studentName;
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

    public String getFeeName(){
        return feeName;
    }
    public void setFeeName(String feeName){
        this.feeName = feeName;
    }

    public String getCourseName(){
        return courseName;
    }
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public double getTotalFee(){
        return totalFee;
    }
    public void setTotalFee(double totalFee){
        this.totalFee = totalFee;
    }

    public double getPaidAmount(){
        return paidAmount;
    }
    public void setPaidAmount(double paidAmount){
        this.paidAmount= paidAmount;
    }

    public double getDueAmount(){
        return dueAmount;
    }
    public void setDueAmount(double dueAmount){
        this.dueAmount = dueAmount;
    }

    public int getStudentFeeId(){return feeId;}
    public void setStudentFeeId(int feeId) {
        this.feeId=feeId;
    }
}
