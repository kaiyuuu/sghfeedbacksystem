package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Staff;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.sghfeedbacksystem.sghfeedbacksystem.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    FeedbackSubCategoryService feedbackSubCategoryService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jackyseah99@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("message sent successfully");
    }

    @Override
    public void statusChangeEmail(Long feedbackId, String statusChange) {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        Staff staff = feedback.getFeedbackAuthor();
        String to = staff.getEmail();
        String subject = "Update on Feedback Status";
        String body = new String();
        if (statusChange.equals("accept")) {
            body = "Feedback ID " + feedbackId +  ": Status Changed from: SUBMITTED to REVIEWING";
        }
        else if (statusChange.equals("reject")) {
            body = "Feedback ID " + feedbackId +  ": Status Changed from: SUBMITTED to CLOSED";
            sendEmail(to, subject, body);
        }
        else {
            body = "Feedback ID " + feedbackId +  ": Status Changed from: REVIEWING to CLOSED";
        }
        sendEmail(to, subject, body);
    }

    @Override
    public void dailyEmailUpdate() {
        System.out.println("Titties");

        List<User> allUsers = userService.getAllUsers();
        List<User> allPO = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getUserRole() == UserRoleEnum.PROCESSOWNER) {
                allPO.add(user);
            }
        }
        List<Long> PO_ID = new ArrayList<>();
        for (User user: allPO) {
            PO_ID.add(user.getUserId());
        }
        Map<Long, Long> subCatPO = new HashMap<>();
        for (Long id : PO_ID) {
            Long subCat_ID = feedbackSubCategoryService.findFeedbackSubCategoryByFeedbackTeamUser(id).getSubCategoryId();
            subCatPO.put(subCat_ID, id);

        }}
}
/*
    dataloader app using runScheduler() (runs scheeduled app - configure timing)

    runScheduler() calls emailServiceImpl's method - dailyEmailUpdate()

    dailyEmailUpdate -> requires to get all PO's
            for each PO
    get all feedback that belong to them (dated for one day);
    count it and send email using sendEmail
*/