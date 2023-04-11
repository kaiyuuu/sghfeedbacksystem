package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.ResponseBodyPublishStatusDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.dto.StartEndDateDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.service.*;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    AdminAnalyticsController adminAnalyticsController;

    //retrieving a list of all feedback under a sub category that the po is assigned to
    //pass in the current user signed in
    @GetMapping("/getFeedbackUnderPO/{userId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksUnderPo(@PathVariable("userId") Long userId) {
        User feedbackTeamUser = userService.findUserById(userId);
        List<FeedbackSubCategory> subCategoriesUnderPO = feedbackSubCategoryService.findAllFeedbackSubCategoryByFeedbackTeamUser(feedbackTeamUser.getUserId());
        List<Feedback> listOfFeedbackUnderPo = new ArrayList<>();
        for (FeedbackSubCategory feedbackSubCategory : subCategoriesUnderPO) {
            List<Feedback> listOfFeedbackUnderSubCategory = feedbackService.findFeedbackBySubCategory(feedbackSubCategory.getSubCategoryId());
            for (Feedback f : listOfFeedbackUnderSubCategory) {
                listOfFeedbackUnderPo.add(f);
            }
        }

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

    @PostMapping("/getFeedbackUnderPOByDate/{userId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksUnderPoByDate(@PathVariable("userId") Long userId, @RequestBody StartEndDateDTO startEndDateDTO) {
        try {
        List<FeedbackDTO> feedbacksUnderPo = getAllFeedbacksUnderPo(userId).getBody();
        LocalDateTime startDate = adminAnalyticsController.convertStringToDate(startEndDateDTO.getStartDateString());
        LocalDateTime endDate = adminAnalyticsController.convertStringToDate(startEndDateDTO.getEndDateString());
        List<FeedbackDTO> feedbacksUnderPoByDate = feedbacksUnderPo.stream().filter(x ->
                LocalDateTime.parse(x.getFeedbackDate()).isAfter(startDate)
                        && LocalDateTime.parse(x.getFeedbackDate()).isBefore(endDate)
          ).collect(Collectors.toList());

        return new ResponseEntity<List<FeedbackDTO>>(feedbacksUnderPoByDate, HttpStatus.OK);

        } catch (ParseException exception) {
            System.out.println("something went wrong with converting string");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getAggregatedFeedbackStatusUnderPo/{userId}")
    public ResponseEntity<Map<String, Integer>> getAggregatedFeedbackStatusUnderPo(@PathVariable("userId") Long userId, @RequestBody StartEndDateDTO startEndDateDTO) {
        List<FeedbackDTO> feedbacksUnderPoByDateDTO = getAllFeedbacksUnderPoByDate(userId, startEndDateDTO).getBody();
        List<Feedback> feedbacksUnderPoByDate = new ArrayList<>();
        for (FeedbackDTO f : feedbacksUnderPoByDateDTO) {
            Feedback feedback = feedbackService.findFeedbackById(f.getFeedbackId());
            feedbacksUnderPoByDate.add(feedback);
        }
        Map<String, Integer> map = feedbackService.getStatusOfFeedbacks(feedbacksUnderPoByDate);
        return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
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
