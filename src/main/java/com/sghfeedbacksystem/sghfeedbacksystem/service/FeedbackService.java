package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Staff;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.CannotDeleteFeedbackUnderReviewException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackCategoryNotFoundException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.FeedbackNotFoundException;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackService {

    //for admin?
    List<Feedback> findAllFeedbackByDate(LocalDateTime startDate, LocalDateTime endDate);

    //for PO
    List<Feedback> findFeedbackByCategory(Long categoryId);
    List<Feedback> findFeedbackBySubCategory(Long subCategoryId);

    //for staff
    List<Feedback> findFeedbackByAuthor(Long userId);
    List<Feedback> findFeedbacksUnderReview();
     Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatusEnum feedbackStatus) throws FeedbackNotFoundException;
     Feedback saveFeedback(Staff staff, Feedback feedback, FeedbackSubCategory FeedbackSubCategory) throws StaffNotFoundException, FeedbackCategoryNotFoundException;
     Long deleteFeedback(Long feedbackId) throws FeedbackNotFoundException, CannotDeleteFeedbackUnderReviewException;


}
