package com.sghfeedbacksystem.sghfeedbacksystem.dto;

public class LoggedInUserRequestDTO {

    public String password;
    public String username;
    //private String userEnum;

    public LoggedInUserRequestDTO() {
    }

    public LoggedInUserRequestDTO(String password, String username) {
        this.password = password;
        this.username = username;
        //this.userEnum = userEnum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getUserEnum() {
//        return userEnum;
//    }
//
//    public void setUserEnum(String userEnum) {
//        this.userEnum = userEnum;
//    }
}
