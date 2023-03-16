package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackSubCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedbackSubCategoryServiceImpl implements FeedbackSubCategoryService {

    @Autowired
    private FeedbackSubCategoryRepository feedbackSubCategoryRepository;

    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    @Autowired
    private FeedbackService feedbackService;

    @Override
    public FeedbackSubCategory findFeedbackSubCategoryByName(String feedbackSubCategory) {
        return feedbackSubCategoryRepository.findFeedbackSubCategoryByName(feedbackSubCategory);
    }

    public FeedbackSubCategory saveFeedbackSubCategory(FeedbackSubCategory feedbackSubCategory, Long feedbackCategoryId) throws FeedbackCategoryNotFoundException {
        FeedbackCategory feedbackCategory = feedbackCategoryService.findFeedbackCategoryById(feedbackCategoryId);
        feedbackSubCategory.setFeedbackCategory(feedbackCategory);
        return feedbackSubCategoryRepository.save(feedbackSubCategory);
    }

    @Override
    public Map<FeedbackSubCategory, Integer> findFeedbackSubCategoryCounts(LocalDateTime startDate, LocalDateTime endDate) {

        Map<FeedbackSubCategory, Integer> map = new HashMap<>();
        List<Feedback> feedbacks = feedbackService.findAllFeedbackByDate(startDate, endDate);

        //count instances of categories
        for (Feedback f : feedbacks) {
            FeedbackSubCategory currentSubCategory = f.getFeedbackSubCategory();
            if (map.containsKey(currentSubCategory)) {
                Integer oldCount = map.get(currentSubCategory);
                oldCount += 1;
                map.replace(currentSubCategory, oldCount);
            } else {
                map.put(currentSubCategory, 1);
            }
        }
        return map;
    }

    public List<Pair<FeedbackSubCategory, Integer>> findPopularFeedbackSubCategories(LocalDateTime startDate, LocalDateTime endDate) {

        Map<FeedbackSubCategory, Integer> map = new HashMap<>();
        List<Feedback> feedbacks = feedbackService.findAllFeedbackByDate(startDate, endDate);

        //count instances of sub categories
        for (Feedback f : feedbacks) {
            FeedbackSubCategory currentSubCategory = f.getFeedbackSubCategory();
            if (map.containsKey(currentSubCategory)) {
                Integer oldCount = map.get(currentSubCategory);
                oldCount += 1;
                map.replace(currentSubCategory, oldCount);
            } else {
                map.put(currentSubCategory, 1);
            }
        }

        //find top 3 categories
        List<FeedbackSubCategory> top3Categories = map.entrySet().stream()
                .sorted(Map.Entry.<FeedbackSubCategory, Integer>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //find the counts of top 3 categories
        List<Pair<FeedbackSubCategory, Integer>> topThreeCategoriesInfo = new ArrayList<>();
        for (FeedbackSubCategory c : top3Categories) {
            topThreeCategoriesInfo.add(new Pair<>(c, map.get(c)));
        }

        return topThreeCategoriesInfo;
    }

}
