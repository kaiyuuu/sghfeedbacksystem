package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;

import java.util.List;

public interface FeedbackCategoryService {

    public List<FeedbackCategory> findAllFeedbackCategory();
    public FeedbackCategory findFeedbackCategoryById(Long feedbackCategoryId) throws FeedbackCategoryNotFoundException;
    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory);

}
