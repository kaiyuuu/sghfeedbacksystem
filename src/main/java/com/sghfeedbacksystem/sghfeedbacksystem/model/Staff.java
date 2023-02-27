package com.sghfeedbacksystem.sghfeedbacksystem.model;

import jakarta.persistence.Entity;

@Entity
public class Staff extends User {
    private String department;

    public Staff() {
    }

    public Staff(String username, String firstName, String lastName, String email, String password, String position, String department) {
        super(username, firstName, lastName, email, password, position);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
