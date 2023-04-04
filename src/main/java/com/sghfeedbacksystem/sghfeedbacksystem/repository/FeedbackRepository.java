package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackCategory;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackSubCategoryId=:feedbackSubCategoryId")
    List<Feedback> findFeedbackBySubCategoryId(Long feedbackSubCategoryId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackCategory.feedbackCategoryId=:feedbackCategoryId")
    List<Feedback> findFeedbackByCategoryId(Long feedbackCategoryId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackAuthor.userId=:userId")
    List<Feedback> findFeedbackByFeedbackAuthorId(Long userId);

    @Query("SELECT f FROM Feedback f WHERE f.feedbackDate >=:localDateTime")
    List<Feedback> findFeedbackByDateGreaterThan(LocalDateTime localDateTime);
}
