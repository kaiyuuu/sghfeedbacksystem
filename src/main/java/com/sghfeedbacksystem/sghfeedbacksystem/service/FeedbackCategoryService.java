package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import jdk.internal.net.http.common.Pair;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackCategoryService {

    public List<FeedbackCategory> findAllFeedbackCategory();
    public FeedbackCategory findFeedbackCategoryById(Long feedbackCategoryId) throws FeedbackCategoryNotFoundException;
    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory);
    public List<Pair<FeedbackCategory, Integer>> findPopularFeedbackCategories(LocalDateTime startDate, LocalDateTime endDate);

}
