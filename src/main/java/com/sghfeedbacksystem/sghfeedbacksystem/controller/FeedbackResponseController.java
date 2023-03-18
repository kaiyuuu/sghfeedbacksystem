package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackResponseService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackSubCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbackResponse")
@CrossOrigin
public class FeedbackResponseController {

    @Autowired
    FeedbackResponseService feedbackResponseService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackSubCategoryService feedbackSubCategoryService;
    @Autowired
    UserService userService;

    //retrieving a list of all feedback under a sub category that the po is assigned to
    //pass in the current user signed in
    @GetMapping("/getFeedbackUnderPO/{userId}")
    public ResponseEntity<List<Feedback>> getAllFeedbacksUnderPo(@PathVariable("userId") Long userId) {
        User feedbackTeamUser = userService.findUserById(userId);
        FeedbackSubCategory subCategory = feedbackSubCategoryService.findFeedbackSubCategoryByFeedbackTeamUser(feedbackTeamUser.getUserId());
        List<Feedback> listOfFeedbackUnderPo = feedbackService.findFeedbackBySubCategory(subCategory.getSubCategoryId());
        return new ResponseEntity<List<Feedback>>(listOfFeedbackUnderPo, HttpStatus.OK);
    }

    @PostMapping("/accept/{feedbackId}")
    public ResponseEntity<FeedbackResponse> acceptFeedback (@PathVariable("feedbackId") Long feedbackId, @RequestBody String feedbackResponseBody) {
        FeedbackResponse feedbackResponse = feedbackResponseService.acceptFeedback(feedbackId, feedbackResponseBody);
        return new ResponseEntity<FeedbackResponse>(feedbackResponse, HttpStatus.OK);
    }

    @PostMapping("/reject/{feedbackId}")
    public ResponseEntity<FeedbackResponse> rejectFeedback (@PathVariable("feedbackId") Long feedbackId, @RequestBody String feedbackResponseBody) {
        FeedbackResponse feedbackResponse = feedbackResponseService.rejectFeedback(feedbackId, feedbackResponseBody);
        return new ResponseEntity<FeedbackResponse>(feedbackResponse, HttpStatus.OK);
    }


}
