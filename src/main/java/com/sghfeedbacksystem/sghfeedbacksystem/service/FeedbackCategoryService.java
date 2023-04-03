package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import jdk.internal.net.http.common.Pair;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface FeedbackCategoryService {

    public List<FeedbackCategory> findAllFeedbackCategory();

    public Map<String, List<String>> findAllCategoriesAndSubCategories();

    public FeedbackCategory findFeedbackCategoryById(Long feedbackCategoryId) throws FeedbackCategoryNotFoundException;

    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory);

    //find number of times each category is tagged for all feedbacks submitted within given time frame
    public Map<FeedbackCategory, Integer> findFeedbackCategoryCounts(LocalDateTime startDate, LocalDateTime endDate);

    //finds top 3 categories where feedback were given most within the given time frame
    public List<Pair<FeedbackCategory, Integer>> findPopularFeedbackCategories(LocalDateTime startDate, LocalDateTime endDate);

}
