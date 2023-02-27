package com.sghfeedbacksystem.sghfeedbacksystem.model;

import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class FeedbackTeam extends User {

    @Enumerated(value = EnumType.STRING)
    private FeedbackRoleEnum role;

    public FeedbackTeam() {
    }

    public FeedbackTeam(String username, String firstName, String lastName, String email, String password, String position, FeedbackRoleEnum role) {
        super(username, firstName, lastName, email, password, position);
        this.role = role;
    }

    public FeedbackRoleEnum getRole() {
        return role;
    }

    public void setRole(FeedbackRoleEnum role) {
        this.role = role;
    }
}
