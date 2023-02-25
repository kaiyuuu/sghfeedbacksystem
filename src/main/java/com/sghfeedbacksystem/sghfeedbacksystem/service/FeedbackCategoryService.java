package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;

import java.util.List;

public interface FeedbackCategoryService {

    public List<FeedbackCategory> findAllFeedbackCategory();
    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory);

}
