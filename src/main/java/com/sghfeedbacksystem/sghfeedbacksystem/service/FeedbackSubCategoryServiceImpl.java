package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackSubCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackSubCategoryServiceImpl implements FeedbackSubCategoryService {

    @Autowired
    private FeedbackSubCategoryRepository feedbackSubCategoryRepository;

    @Autowired
    private FeedbackCategoryService feedbackCategoryService;

    public FeedbackSubCategory saveFeedbackSubCategory(FeedbackSubCategory feedbackSubCategory, Long feedbackCategoryId) throws FeedbackCategoryNotFoundException {
        FeedbackCategory feedbackCategory = feedbackCategoryService.findFeedbackCategoryById(feedbackCategoryId);
        feedbackSubCategory.setFeedbackCategory(feedbackCategory);
        return feedbackSubCategoryRepository.save(feedbackSubCategory);
    }

}
