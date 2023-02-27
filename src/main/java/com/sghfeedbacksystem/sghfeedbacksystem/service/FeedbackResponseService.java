package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;

import java.util.List;

public interface FeedbackResponseService {

     List<FeedbackResponse> findAllFeedbackResponseByFeedbackId(Long feedbackId);
     List<FeedbackResponse> findAllFeedbackResponseByAuthorId(Long feedbackResponseAuthorId);

     //need to call email service to notify staff
//     FeedbackResponse saveFeedbackResponse(FeedbackResponse feedbackResponse, Feedback feedback, FeedbackTeam feedbackResponseAuthor);
//     List<FeedbackResponse> findFeedbackResponseByAuthor();

}
