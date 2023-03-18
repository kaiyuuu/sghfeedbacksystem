package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Staff;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackService {

    //for admin?
    List<Feedback> findAllFeedbackByDate(LocalDateTime startDate, LocalDateTime endDate);

    //for PO
    List<Feedback> findFeedbackByCategory(Long categoryId);
    List<Feedback> findFeedbackBySubCategory(Long subCategoryId);
    Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatusEnum feedbackStatus) throws FeedbackNotFoundException;
    Feedback publishFeedback(Long feedbackId);
    //for staff
    List<Feedback> findFeedbackByAuthor(Long userId) throws UserNotFoundException;
    List<Feedback> findFeedbacksUnderReview();
    List<Feedback> findFeedbacksSubmitted();
    List<Feedback> findFeedbacksPublished();

    Feedback findFeedbackById(Long feedbackId);


    Feedback saveFeedback(Staff staff, Feedback feedback, FeedbackSubCategory FeedbackSubCategory) throws StaffNotFoundException, FeedbackCategoryNotFoundException;
     Long deleteFeedback(Long feedbackId) throws FeedbackNotFoundException, CannotDeleteFeedbackUnderReviewException;



}
