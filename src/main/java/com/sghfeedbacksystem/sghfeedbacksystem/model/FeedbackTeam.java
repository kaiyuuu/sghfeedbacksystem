package com.sghfeedbacksystem.sghfeedbacksystem.model;

import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class FeedbackTeam extends User {


    public FeedbackTeam() {
    }

    public FeedbackTeam(String username, String firstName, String lastName, String email, String password, String position, UserRoleEnum userRole) {
        super(username, firstName, lastName, email, password, position, userRole);
    }
}
