package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import jdk.internal.net.http.common.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface FeedbackSubCategoryService {

    public FeedbackSubCategory findFeedbackSubCategoryByName(String feedbackSubCategory);

    public FeedbackSubCategory findFeedbackSubCategoryByFeedbackTeamUser(Long userId);

    public FeedbackSubCategory saveFeedbackSubCategory(FeedbackSubCategory feedbackSubCategory, Long feedbackCategoryId) throws FeedbackCategoryNotFoundException;

    //find number of times each subcategory is tagged for all feedbacks submitted within given time frame
    public Map<FeedbackSubCategory, Integer> findFeedbackSubCategoryCounts(LocalDateTime startDate, LocalDateTime endDate);

    //finds top 3 subcategory where feedback were given most within the given time frame
    public List<Pair<FeedbackSubCategory, Integer>> findPopularFeedbackSubCategories(LocalDateTime startDate, LocalDateTime endDate);

}
