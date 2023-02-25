package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategory, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackCategory.feedbackCategoryId=:feedbackCategoryId")
    List<Feedback> findAllFeedbackByFeedbackCategory(Long feedbackCategoryId);
}
