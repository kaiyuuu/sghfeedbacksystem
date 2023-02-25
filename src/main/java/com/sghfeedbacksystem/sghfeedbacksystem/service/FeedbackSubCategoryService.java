package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;

public interface FeedbackSubCategoryService {

    public FeedbackSubCategory saveFeedbackSubCategory(FeedbackSubCategory feedbackSubCategory, Long feedbackCategoryId) throws FeedbackCategoryNotFoundException;
}
