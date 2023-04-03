package com.sghfeedbacksystem.sghfeedbacksystem.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void statusChangeEmail(Long feedbackId, String statusChange);
    void dailyEmailUpdate();
}
