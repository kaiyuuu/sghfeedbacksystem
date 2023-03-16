package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.Feedback;
import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackResponseRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackTeamRepository;
import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import com.sghfeedbacksystem.sghfeedbacksystem.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackResponseServiceImpl implements FeedbackResponseService{

    @Autowired
    private FeedbackResponseRepository feedbackResponseRepository;

    @Autowired
    private FeedbackTeamRepository feedbackTeamRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackResponse findFeedbackResponseByFeedbackId(Long feedbackId) {
        return feedbackResponseRepository.findFeedbackResponsesByFeedbackId(feedbackId);
    }

    @Override
    public List<FeedbackResponse> findAllFeedbackResponseByAuthorId(Long feedbackResponseAuthorId) throws UserNotFoundException {
        if(feedbackTeamRepository.findById(feedbackResponseAuthorId).get() == null) {
            throw new UserNotFoundException();
        }

        return feedbackResponseRepository.findAllFeedbackResponseByAuthorId(feedbackResponseAuthorId);
    }

    @Override
    public FeedbackResponse acceptFeedback(Long feedbackId, String feedbackResponseBody) {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        feedback.setFeedbackStatus(FeedbackStatusEnum.CLOSED);
        FeedbackResponse feedbackResponse = createFeedback(feedback, feedbackResponseBody);

        return feedbackResponse;
    }

    @Override
    public FeedbackResponse rejectFeedback(Long feedbackId, String feedbackResponseBody) {
        Feedback feedback = feedbackRepository.findById(feedbackId).get();
        feedback.setFeedbackStatus(FeedbackStatusEnum.REVIEWING);
        FeedbackResponse feedbackResponse = createFeedback(feedback, feedbackResponseBody);

        return feedbackResponse;
    }

    @Override
    public FeedbackResponse createFeedback(Feedback feedback, String feedbackResponseBody) {
        FeedbackResponse newFeedbackResponse = new FeedbackResponse();
        newFeedbackResponse.setFeedbackResponseBody(feedbackResponseBody);
        newFeedbackResponse.setFeedbackResponseDate(LocalDateTime.now());
        newFeedbackResponse.setFeedbackResponseTitle("Response to feedback titled: " + feedback.getFeedbackTitle());
        newFeedbackResponse.setFeedbackResponseAuthor(feedback.getFeedbackSubCategory().getFeedbackSubCategoryPo());
        newFeedbackResponse.setFeedback(feedback);

        feedbackRepository.save(feedback);
        feedbackResponseRepository.save(newFeedbackResponse);
        return newFeedbackResponse;
    }
}
