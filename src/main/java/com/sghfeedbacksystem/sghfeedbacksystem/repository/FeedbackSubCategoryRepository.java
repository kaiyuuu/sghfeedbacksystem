package com.sghfeedbacksystem.sghfeedbacksystem.repository;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackSubCategoryRepository extends JpaRepository<FeedbackSubCategory, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.feedbackSubCategory.feedbackSubCategoryId=:feedbackSubCategoryId")
    List<Feedback> findAllFeedbackBySubCategory(Long feedbackSubCategoryId);

}
