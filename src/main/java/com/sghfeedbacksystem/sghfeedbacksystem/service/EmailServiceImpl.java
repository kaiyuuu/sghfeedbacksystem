package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackSubCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.UserRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.UserRoleEnum;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    UserRepository userRepository;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hsienjiejiayingsachinkaiyu@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("message sent successfully");
    }

    @Override
    public void sendEmail2(String to, String subject, String body) {
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jackyseah99@outlook.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body, true);*/
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("hsienjiejiayingsachinkaiyu@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            System.out.println("message sent successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong creating MimeMessageHelper");
        }
    }

    @Override
    public void statusChangeEmail(Long feedbackId, String statusChange) {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        Staff staff = feedback.getFeedbackAuthor();
        String to = staff.getEmail();
        String subject = "Update on Feedback Status! ";
        String body = new String();
        if (statusChange.equals("accept")) {
            body += "<h1 style='font-family:serif;font-size:16;color:black;'>Feedback ID " +
                    feedbackId +
                    " Status Changed from: SUBMITTED to REVIEWING</h1>";
        }
        else if (statusChange.equals("reject")) {
            body += "<h1 style='font-family:serif;font-size:16;color:black;'>Feedback ID " +
                    feedbackId +
                    " Status Changed from: SUBMITTED to CLOSED</h1>";
        }
        else {
            body += "<h1 style='font-family:serif;font-size:16;color:black;'>Feedback ID "
                    + feedbackId +
                    " Status Changed from: REVIEWING to CLOSE</h1>";
        }
        String feedbackTitle = feedback.getFeedbackTitle();
        body += "<h1 style='font-family:serif;font-size:14;color:black;'>Title: <b>" + feedbackTitle + "</b></h1>";
        String feedbackBody = feedback.getFeedbackBody();
        body += "<h2 style='font-family:serif;font-size:14;color:black;'>Description: " + feedbackBody + "</h2>";
        //body += "<img src='../util/sgh-logo.jpg' alt='hello'>";
        sendEmail2(to, subject, body);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * *")
    public void dailyEmailUpdate() {
        List<User> allPO = userRepository.findUsersByUserRole(UserRoleEnum.PROCESSOWNER);
        Map<Long, Long> PO_ID = new HashMap<>();
        for (User user : allPO) {
            PO_ID.put(user.getUserId(), (long) 0);
        }
        LocalDateTime dateBefore1Day = LocalDateTime.now().minusDays(1);
        List<Feedback> allFeedbacks = feedbackRepository.findFeedbackByDateGreaterThan(dateBefore1Day);
        for (Feedback feedback : allFeedbacks) {
            FeedbackSubCategory feedbackSubCategory = feedback.getFeedbackSubCategory();
            FeedbackTeam feedbackPO = feedbackSubCategory.getFeedbackSubCategoryPo();
            Long feedbackPO_ID = feedbackPO.getUserId();
            PO_ID.put(feedbackPO_ID, PO_ID.get(feedbackPO_ID) + 1);
        }

        for (Map.Entry<Long,Long> entry : PO_ID.entrySet()) {
            if (entry.getValue() != 0) {

                String to = userRepository.findById(entry.getKey()).get().getEmail();
                String subject = "New Feedbacks For You";
                String message = "You have " + entry.getValue() + " new feedbacks to review!";
                sendEmail(to, subject, message);
                //System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
        /*
        for (long id : PO_ID) {
            System.out.println(id);
        }*/

        /*
        for (Feedback feedback : allFeedbacks) {
            System.out.println(feedback.getFeedbackId());
        }*/

    }
        /*
        Map<Long, Long> subCatPO = new HashMap<>();
        for (Long id : PO_ID) {
            Long subCat_ID = feedbackSubCategoryService.findFeedbackSubCategoryByFeedbackTeamUser(id).getSubCategoryId();
            subCatPO.put(subCat_ID, id);

        }}*/
}
/*
    dataloader app using runScheduler() (runs scheeduled app - configure timing)

    runScheduler() calls emailServiceImpl's method - dailyEmailUpdate()

    dailyEmailUpdate -> requires to get all PO's
            for each PO
    get all feedback that belong to them (dated for one day);
    count it and send email using sendEmail
*/