package com.sghfeedbacksystem.sghfeedbacksystem.controller;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackCategoryService;
import com.sghfeedbacksystem.sghfeedbacksystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FeedbackCategoryService feedbackCategoryService;

    @GetMapping("/getPublishedFeedbacks")
    public ResponseEntity<List<Feedback>> getPublishedFeedback() throws Exception {
        try {
            List<Feedback> publishedFeedbacks = removePasswordFromFeedback(feedbackService.findFeedbacksPublished());
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

    @PostMapping("/publishFeedback")
    public ResponseEntity<Feedback> publishFeedback(Long feedbackId) {
        return null;
    }

    public List<Feedback> removePasswordFromFeedback(List<Feedback> feedbacks) {
        for (Feedback f : feedbacks) {
            f.getFeedbackAuthor().setPassword("");
            f.getFeedbackSubCategory().setFeedbackSubCategoryDescription("");
            f.getFeedbackSubCategory().getFeedbackCategory().setFeedbackCategoryDescription("");
        }
        return feedbacks;
    }
}
