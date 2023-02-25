package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackCategoryServiceImpl implements FeedbackCategoryService {

    @Autowired
    private FeedbackCategoryRepository feedbackCategoryRepository;

    public List<FeedbackCategory> findAllFeedbackCategory() {
        return feedbackCategoryRepository.findAll();
    }

    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory) {
        return feedbackCategoryRepository.save(feedbackCategory);
    }

}
