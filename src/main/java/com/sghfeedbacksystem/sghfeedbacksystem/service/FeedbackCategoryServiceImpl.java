package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackCategoryRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
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

    public FeedbackCategory findFeedbackCategoryById(Long feedbackCategoryId) throws FeedbackCategoryNotFoundException{
        FeedbackCategory f = feedbackCategoryRepository.findById(feedbackCategoryId).get();
        if(f != null) {
            return f;
        } else {
            throw new FeedbackCategoryNotFoundException();
        }
    }

    public FeedbackCategory saveFeedbackCategory(FeedbackCategory feedbackCategory) {
        return feedbackCategoryRepository.save(feedbackCategory);
    }

}
