package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
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

}
