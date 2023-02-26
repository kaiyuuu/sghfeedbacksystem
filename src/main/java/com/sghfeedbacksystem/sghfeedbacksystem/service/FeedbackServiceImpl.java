package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements  FeedbackService{

    @Override
    public List<Feedback> findAllFeedbackByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<Feedback> findFeedbackByCategory(Long categoryId) {
        return null;
    }

    @Override
    public List<Feedback> findFeedbackBySubCategory(Long subCategoryId) {
        return null;
    }

    @Override
    public List<Feedback> findFeedbackByAuthor(Long userId) {
        return null;
    }

    @Override
    public List<Feedback> findFeedbacksUnderReview() {
        return null;
    }
}
