package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.dto.ResponseBodyPublishStatusDTO;
import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface FeedbackResponseService {

     FeedbackResponse findFeedbackResponseByFeedbackId(Long feedbackId);
     List<FeedbackResponse> findAllFeedbackResponseByAuthorId(Long feedbackResponseAuthorId) throws UserNotFoundException;

     FeedbackResponse acceptFeedback(Long feedbackId, ResponseBodyPublishStatusDTO responseBodyPublishStatusDTO);

     FeedbackResponse rejectFeedback(Long feedbackId, ResponseBodyPublishStatusDTO responseBodyPublishStatusDTO);

     FeedbackResponse createFeedback(Feedback feedback, String feedbackResponseBody);

     //need to call email service to notify staff
//     FeedbackResponse saveFeedbackResponse(FeedbackResponse feedbackResponse, Feedback feedback, FeedbackTeam feedbackResponseAuthor);
//     List<FeedbackResponse> findFeedbackResponseByAuthor();

}
