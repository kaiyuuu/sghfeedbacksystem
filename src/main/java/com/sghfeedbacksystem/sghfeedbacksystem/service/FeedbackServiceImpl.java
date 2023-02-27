package com.sghfeedbacksystem.sghfeedbacksystem.service;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackRepository feedbackRepository;


    //not tested
    @Override
    public List<Feedback> findAllFeedbackByDate(LocalDateTime startDate, LocalDateTime endDate) {

        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x ->
                x.getFeedbackDate().isAfter(startDate)
                        && x.getFeedbackDate().isBefore(endDate)).collect(Collectors.toList());

    }

    //not tested
    @Override
    public List<Feedback> findFeedbackByCategory(Long categoryId) {
        return feedbackRepository.findFeedbackByCategoryId(categoryId);
    }

    //not tested
    @Override
    public List<Feedback> findFeedbackBySubCategory(Long subCategoryId) {
        return feedbackRepository.findFeedbackBySubCategoryId(subCategoryId);
    }

    @Override
    public List<Feedback> findFeedbackByAuthor(Long userId) {
        return null;
    }

    //not tested
    @Override
    public List<Feedback> findFeedbacksUnderReview() {

        List<Feedback> allFeedback = feedbackRepository.findAll();
        return allFeedback.stream().filter(x ->
                x.getFeedbackStatus().equals(FeedbackStatusEnum.REVIEWING))
                .collect(Collectors.toList());

    }

    @Override
    public Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatusEnum feedbackStatus) throws FeedbackNotFoundException {

        Feedback feedbackToUpdate = feedbackRepository.findById(feedbackId).get();
        if(feedbackToUpdate!=null) {
            feedbackToUpdate.setFeedbackStatus(feedbackStatus);
            return feedbackRepository.save(feedbackToUpdate);
        } else {
            throw new FeedbackNotFoundException();
        }
    }
}
