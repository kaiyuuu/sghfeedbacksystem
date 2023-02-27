package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM FeedbackResponse f WHERE f.feedback.feedbackId=:feedbackId")
    List<FeedbackResponse> findAllFeedbackResponsesByFeedbackId(Long feedbackId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackSubCategoryId=:feedbackSubCategoryId")
    List<Feedback> findFeedbackBySubCategoryId(Long feedbackSubCategoryId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackCategory.feedbackCategoryId=:feedbackCategoryId")
    List<Feedback> findFeedbackByCategoryId(Long feedbackCategoryId);

}
