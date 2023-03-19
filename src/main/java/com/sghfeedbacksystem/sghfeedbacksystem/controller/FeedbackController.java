package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.service.*;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.*;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FeedbackResponseService feedbackResponseService;
    @Autowired
    FeedbackCategoryService feedbackCategoryService;
    @Autowired
    FeedbackSubCategoryService feedbackSubCategoryService;
    @Autowired
    UserService userService;

    @GetMapping("/getPublishedFeedbacks")
    public ResponseEntity<List<Feedback>> getPublishedFeedback() throws Exception {
        try {
            List<Feedback> publishedFeedbacks = removePasswordFromFeedbacks(feedbackService.findFeedbacksPublished());
            return new ResponseEntity<List<Feedback>>(publishedFeedbacks, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCategories")
    public ResponseEntity<Map<String, List<String>>> getCategories() throws Exception {
        try {
            Map<String, List<String>> CategoryMap = feedbackCategoryService.findAllCategoriesAndSubCategories();
            return new ResponseEntity<Map<String, List<String>>>(CategoryMap, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //get all feedback and feedback responses by a particular user by Id
    @GetMapping("/getFeedbacksByUser/{userId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByUser(@PathVariable("userId") Long userId) {
        try {
            List<Feedback> feedbacks = feedbackService.findFeedbackByAuthor(userId);
            List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
            for(Feedback f : feedbacks) {
//FeedbackDTO(Long userID, String category, String subcategory, Boolean anonymity, String title, String feedback) {
                    FeedbackDTO feedbackDTO = new FeedbackDTO(userId,f.getFeedbackAuthor().getUsername(),
                            f.getFeedbackSubCategory().getFeedbackCategory().getFeedbackCategoryName(),
                            f.getFeedbackSubCategory().getFeedbackSubcategoryName(),
                            f.getAnonymous(), f.getFeedbackTitle(), f.getFeedbackBody());
                    FeedbackResponse feedbackResponse = feedbackResponseService.findFeedbackResponseByFeedbackId(f.getFeedbackId());
                    if(feedbackResponse != null) {
                        feedbackResponse.setFeedback(null);
                        feedbackResponse.getFeedbackResponseAuthor().setPassword(null);
                        feedbackDTO.setFeedbackResponse(feedbackResponse);
                    }
                    feedbackDTOS.add(feedbackDTO);
            }
            return new ResponseEntity<List<FeedbackDTO>>(feedbackDTOS,HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //not tested
    @DeleteMapping("/deleteFeedback/{feedbackId}")
    public ResponseEntity<Long> deleteFeedback(@PathVariable("feedbackId") Long feedbackId) {
        try {
            return new ResponseEntity<Long>(feedbackService.deleteFeedback(feedbackId), HttpStatus.OK);
        } catch (FeedbackNotFoundException | CannotDeleteFeedbackUnderReviewException exception) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/publishFeedback/{feedbackId}")
    public ResponseEntity<Feedback> publishFeedback(@PathVariable("feedbackId") Long feedbackId) {
        Feedback publishedFeedback = feedbackService.publishFeedback(feedbackId);
        publishedFeedback = removePasswordFromFeedback(publishedFeedback);
        return new ResponseEntity<Feedback>(publishedFeedback, HttpStatus.OK);
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        LocalDateTime today = LocalDateTime.now();
        try {
            Staff author = (Staff) userService.findUserById(feedbackDTO.getUserID());
            Feedback feedbackToCreate = new Feedback(feedbackDTO.getTitle(), feedbackDTO.getFeedback(), feedbackDTO.getAnonymity(), today);
            FeedbackSubCategory feedbackSubCategory = feedbackSubCategoryService.findFeedbackSubCategoryByName(feedbackDTO.getSubcategory());
            Feedback createdFeedback = feedbackService.saveFeedback(author, feedbackToCreate, feedbackSubCategory);
            createdFeedback = removePasswordFromFeedback(createdFeedback);
            return new ResponseEntity<Feedback>(createdFeedback, HttpStatus.OK);
//         Feedback saveFeedback(Staff staff, Feedback feedback, FeedbackSubCategory feedbackSubCategory) throws StaffNotFoundException, FeedbackCategoryNotFoundException
        } catch (StaffNotFoundException | FeedbackCategoryNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<Feedback> removePasswordFromFeedbacks(List<Feedback> feedbacks) {
        List<Feedback> updatedFeedbacks = new ArrayList<>();
        for (Feedback f : feedbacks) {
            updatedFeedbacks.add(removePasswordFromFeedback(f));
        }
        return updatedFeedbacks;
    }

    public Feedback removePasswordFromFeedback(Feedback feedback) {
        feedback.getFeedbackAuthor().setPassword("");
        feedback.getFeedbackSubCategory().setFeedbackSubCategoryDescription("");
        feedback.getFeedbackSubCategory().getFeedbackCategory().setFeedbackCategoryDescription("");
        return feedback;
    }

}
