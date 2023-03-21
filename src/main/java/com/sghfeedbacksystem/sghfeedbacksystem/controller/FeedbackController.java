package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.service.*;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.*;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<FeedbackDTO>> getPublishedFeedback() throws Exception {
        try {
            List<Feedback> publishedFeedbacks = removePasswordFromFeedbacks(feedbackService.findFeedbacksPublished());
            List<FeedbackDTO> publishedFeedbackDTOs = new ArrayList<>();
            for(Feedback f : publishedFeedbacks) {
                FeedbackDTO feedbackDTO = convertFeedbackToFeedbackDTO(f);
                publishedFeedbackDTOs.add(feedbackDTO);
            }
            return new ResponseEntity<List<FeedbackDTO>>(publishedFeedbackDTOs, HttpStatus.OK);
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
                    FeedbackDTO feedbackDTO = new FeedbackDTO(userId,f.getFeedbackId(), f.getFeedbackAuthor().getUsername(),
                            f.getFeedbackSubCategory().getFeedbackCategory().getFeedbackCategoryName(),
                            f.getFeedbackSubCategory().getFeedbackSubcategoryName(),
                            f.getAnonymous(), f.getFeedbackTitle(), f.getFeedbackBody(),
                            f.getFeedbackDate().toString(),
                            f.getFeedbackStatus());
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

    @GetMapping("/getAllFeedback")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.findAllFeedbackByDate(LocalDateTime.MIN, LocalDateTime.now());
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        for (Feedback f : feedbacks) {
            FeedbackDTO feedbackDTO = convertFeedbackToFeedbackDTO(f);
            feedbackDTOS.add(feedbackDTO);
        }
        return new ResponseEntity<List<FeedbackDTO>>(feedbackDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/deleteFeedback/{feedbackId}")
    public ResponseEntity<Long> deleteFeedback(@PathVariable("feedbackId") Long feedbackId) {
        try {
            FeedbackResponse response = feedbackResponseService.findFeedbackResponseByFeedbackId(feedbackId);
            return new ResponseEntity<Long>(feedbackService.deleteFeedback(feedbackId), HttpStatus.OK);
        } catch (FeedbackNotFoundException | CannotDeleteFeedbackUnderReviewException exception) {
            System.out.println("cannot delete feedback under review");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/publishFeedback/{feedbackId}")
    public ResponseEntity<FeedbackDTO> publishFeedback(@PathVariable("feedbackId") Long feedbackId) {
        Feedback publishedFeedback = feedbackService.publishFeedback(feedbackId);
        publishedFeedback = removePasswordFromFeedback(publishedFeedback);
        FeedbackDTO feedbackDTO = convertFeedbackToFeedbackDTO(publishedFeedback);
        return new ResponseEntity<FeedbackDTO>(feedbackDTO, HttpStatus.OK);
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        LocalDateTime today = LocalDateTime.now();
        try {
            Staff author = (Staff) userService.findUserById(feedbackDTO.getUserID());
            Feedback feedbackToCreate = new Feedback(feedbackDTO.getTitle(), feedbackDTO.getFeedback(), feedbackDTO.getAnonymity(), today);
            FeedbackSubCategory feedbackSubCategory = feedbackSubCategoryService.findFeedbackSubCategoryByName(feedbackDTO.getSubcategory());
            Feedback createdFeedback = feedbackService.saveFeedback(author, feedbackToCreate, feedbackSubCategory);
            createdFeedback = removePasswordFromFeedback(createdFeedback);
            FeedbackDTO createdFeedbackDTO = convertFeedbackToFeedbackDTO(createdFeedback);
            return new ResponseEntity<FeedbackDTO>(createdFeedbackDTO, HttpStatus.OK);
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

    public FeedbackDTO convertFeedbackToFeedbackDTO(Feedback f) {
        FeedbackDTO feedbackDTO = new FeedbackDTO(f.getFeedbackAuthor().getUserId(),
                f.getFeedbackId(),
                f.getFeedbackAuthor().getUsername(),
                f.getFeedbackSubCategory().getFeedbackCategory().getFeedbackCategoryName(),
                f.getFeedbackSubCategory().getFeedbackSubcategoryName(),
                f.getAnonymous(), f.getFeedbackTitle(), f.getFeedbackBody(),f.getFeedbackDate().toString(),
                f.getFeedbackStatus());
        FeedbackResponse feedbackResponse = feedbackResponseService.findFeedbackResponseByFeedbackId(f.getFeedbackId());
        if (feedbackResponse != null) {
            feedbackResponse.setFeedback(null);
            feedbackResponse.getFeedbackResponseAuthor().setPassword(null);
            feedbackDTO.setFeedbackResponse(feedbackResponse);
        }
        return feedbackDTO;
    }

}
