package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import jdk.internal.net.http.common.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedbackCategoryServiceImpl implements FeedbackCategoryService {

    @Autowired
    private FeedbackCategoryRepository feedbackCategoryRepository;
    @Autowired
    private FeedbackService feedbackService;

    public List<FeedbackCategory> findAllFeedbackCategory() {
        return feedbackCategoryRepository.findAll();
    }

    public FeedbackCategory findFeedbackCategoryById(Long feedbackCategoryId) throws FeedbackCategoryNotFoundException {
        FeedbackCategory f = feedbackCategoryRepository.findById(feedbackCategoryId).get();
        if (f != null) {
            return f;
        } else {
            throw new FeedbackCategoryNotFoundException();
        }
    }

    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory) {
        return feedbackCategoryRepository.save(feedbackCategory);
    }

    //finds top 3 categories where feedback were given most within the given time frame
    public List<Pair<FeedbackCategory, Integer>> findPopularFeedbackCategories(LocalDateTime startDate, LocalDateTime endDate) {

        Map<FeedbackCategory, Integer> map = findFeedbackCategoryCounts(startDate, endDate);

        //find top 3 categories
        List<FeedbackCategory> top3Categories = map.entrySet().stream()
                .sorted(Map.Entry.<FeedbackCategory, Integer>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //find the counts of top 3 categories
        List<Pair<FeedbackCategory, Integer>> topThreeCategoriesInfo = new ArrayList<>();
        for (FeedbackCategory c : top3Categories) {
            topThreeCategoriesInfo.add(new Pair<>(c, map.get(c)));
        }

        return topThreeCategoriesInfo;
    }

    //find number of times each category is tagged for all feedbacks submitted within given time frame
    @Override
    public Map<FeedbackCategory, Integer> findFeedbackCategoryCounts(LocalDateTime startDate, LocalDateTime endDate) {

        Map<FeedbackCategory, Integer> map = new HashMap<>();
        List<Feedback> feedbacks = feedbackService.findAllFeedbackByDate(startDate, endDate);

        //count instances of categories
        for (Feedback f : feedbacks) {
            FeedbackCategory currentCategory = f.getFeedbackSubCategory().getFeedbackCategory();
            if (map.containsKey(currentCategory)) {
                Integer oldCount = map.get(currentCategory);
                oldCount += 1;
                map.replace(currentCategory, oldCount);
            } else {
                map.put(currentCategory, 1);
            }
        }
        return map;
    }
}
