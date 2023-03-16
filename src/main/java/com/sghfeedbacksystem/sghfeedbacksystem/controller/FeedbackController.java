package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.FeedbackDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.*;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackSubCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.UserService;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.StaffNotFoundException;
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
