package com.sghfeedbacksystem.sghfeedbacksystem.model;

import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import jakarta.persistence.Entity;

@Entity
public class Staff extends User {
    //private String department;

    public Staff() {
    }

    public Staff(String username, String firstName, String lastName, String email, String password, String position, UserRoleEnum userRole) {
        super(username, firstName, lastName, email, password, position, userRole);
        //this.department = department;
    }



//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
}
