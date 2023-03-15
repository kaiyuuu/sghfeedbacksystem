package com.sghfeedbacksystem.sghfeedbacksystem.dto;

public class LoggedInUserDTO {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userEnum;

    public LoggedInUserDTO() {
    }

    public LoggedInUserDTO(Long userId, String username, String firstName, String lastName, String email, String password, String userEnum) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userEnum = userEnum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEnum() {
        return userEnum;
    }

    public void setUserEnum(String userEnum) {
        this.userEnum = userEnum;
    }
}
