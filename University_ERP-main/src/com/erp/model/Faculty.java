package com.erp.model;

public class Faculty {
    private String facutlyId;
    private String name;
    private String email;
    private String department;

    public Faculty(String facultyId, String name, String email, String department){
        this.facutlyId = facultyId;
        this.name = name;
        this.email = email;
        this.department = department;
    }
    public String getFacutlyId(){
        return facutlyId;
    }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getDepartment(){
        return department;
    }
}
