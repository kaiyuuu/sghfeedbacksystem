package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.ResponseBodyPublishStatusDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.service.*;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feedbackResponse")
@CrossOrigin
public class
FeedbackResponseController {

    @Autowired
    FeedbackResponseService feedbackResponseService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackSubCategoryService feedbackSubCategoryService;
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    //retrieving a list of all feedback under a sub category that the po is assigned to
    //pass in the current user signed in
    @GetMapping("/getFeedbackUnderPO/{userId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksUnderPo(@PathVariable("userId") Long userId) {
        User feedbackTeamUser = userService.findUserById(userId);
        FeedbackSubCategory subCategory = feedbackSubCategoryService.findFeedbackSubCategoryByFeedbackTeamUser(feedbackTeamUser.getUserId());
        List<Feedback> listOfFeedbackUnderPo = feedbackService.findFeedbackBySubCategory(subCategory.getSubCategoryId());
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        for (Feedback f : listOfFeedbackUnderPo) {
            FeedbackDTO feedbackDTO = new FeedbackDTO(userId,f.getFeedbackId(), f.getFeedbackAuthor().getUsername(),
                    f.getFeedbackSubCategory().getFeedbackCategory().getFeedbackCategoryName(),
                    f.getFeedbackSubCategory().getFeedbackSubcategoryName(),
                    f.getAnonymous(), f.getFeedbackTitle(), f.getFeedbackBody(),
                    f.getFeedbackDate().toString(),
                    f.getFeedbackStatus());
            FeedbackResponse feedbackResponse = feedbackResponseService.findFeedbackResponseByFeedbackId(f.getFeedbackId());
            if (feedbackResponse != null) {
                feedbackResponse.setFeedback(null);
                feedbackResponse.getFeedbackResponseAuthor().setPassword(null);
                feedbackDTO.setFeedbackResponse(feedbackResponse);
            }
            feedbackDTOS.add(feedbackDTO);
        }
        return new ResponseEntity<List<FeedbackDTO>>(feedbackDTOS, HttpStatus.OK);
    }

    @PostMapping("/accept/{feedbackId}")
    public ResponseEntity<FeedbackResponse> acceptFeedback (@PathVariable("feedbackId") Long feedbackId, @RequestBody ResponseBodyPublishStatusDTO responseBodyPublishStatusDTO) {
        FeedbackResponse feedbackResponse = feedbackResponseService.acceptFeedback(feedbackId, responseBodyPublishStatusDTO);
        emailService.statusChangeEmail(feedbackId, "accept");
        return new ResponseEntity<FeedbackResponse>(feedbackResponse, HttpStatus.OK);
    }

    @PostMapping("/reject/{feedbackId}")
    public ResponseEntity<FeedbackResponse> rejectFeedback (@PathVariable("feedbackId") Long feedbackId, @RequestBody ResponseBodyPublishStatusDTO responseBodyPublishStatusDTO) {
        FeedbackResponse feedbackResponse = feedbackResponseService.rejectFeedback(feedbackId, responseBodyPublishStatusDTO);
        emailService.statusChangeEmail(feedbackId, "reject");
        return new ResponseEntity<FeedbackResponse>(feedbackResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{feedbackId}")
    public ResponseEntity<FeedbackResponse> closeFeedbackUpdateResponse (@PathVariable("feedbackId") Long feedbackId, @RequestBody ResponseBodyPublishStatusDTO responseBodyPublishStatusDTO) {
        FeedbackResponse feedbackResponse = feedbackResponseService.closeFeedbackUpdateResponse(feedbackId, responseBodyPublishStatusDTO);
        emailService.statusChangeEmail(feedbackId, "close");
        return new ResponseEntity<FeedbackResponse>(feedbackResponse, HttpStatus.OK);
    }

}
