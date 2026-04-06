package com.erp.model;

public class Payment {
    int paymentId;
    int studentFeeId;
    double amountPaid;
    java.sql.Timestamp paymentDate;
    boolean isCompleted;

    public int getPaymentId(){
        return paymentId;
    }
    public void setPaymentId(int paymentId){
        this.paymentId = paymentId;
    }

    public int getStudentFeeId(){
        return studentFeeId;
    }
    public void setStudentFeeId(int studentFeeId){
        this.studentFeeId = studentFeeId;
    }

    public double getAmountPaid(){
        return amountPaid;
    }
    public void setAmountPaid(double amountPaid){
        this.amountPaid = amountPaid;
    }

    public java.sql.Timestamp getPaymentDate(){
        return paymentDate;
    }
    public void setPaymentDate(java.sql.Timestamp paymentDate){
        this.paymentDate = paymentDate;
    }

    // ⚠️ Boolean naming convention matters
    public boolean isCompleted(){
        return isCompleted;
    }
    public void setCompleted(boolean completed){
        this.isCompleted = completed;
    }
}
