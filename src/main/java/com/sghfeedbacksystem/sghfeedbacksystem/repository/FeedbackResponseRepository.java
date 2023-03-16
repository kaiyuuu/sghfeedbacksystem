package com.sghfeedbacksystem.sghfeedbacksystem.repository;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackResponseRepository extends JpaRepository<FeedbackResponse, Long> {

    @Query("SELECT f FROM FeedbackResponse f WHERE f.feedback.feedbackId=:feedbackId")
    FeedbackResponse findFeedbackResponsesByFeedbackId(Long feedbackId);

    @Query("SELECT f FROM FeedbackResponse f WHERE f.feedbackResponseAuthor.userId=:userId")
    List<FeedbackResponse> findAllFeedbackResponseByAuthorId(Long userId);

}
