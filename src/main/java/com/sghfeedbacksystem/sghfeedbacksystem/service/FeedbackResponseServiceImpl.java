package com.sghfeedbacksystem.sghfeedbacksystem.service;

import com.sghfeedbacksystem.sghfeedbacksystem.model.FeedbackResponse;
import com.sghfeedbacksystem.sghfeedbacksystem.repository.FeedbackResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackResponseServiceImpl implements FeedbackResponseService{

    @Autowired
    private FeedbackResponseRepository feedbackResponseRepository;

    @Override
    public List<FeedbackResponse> findAllFeedbackResponseByFeedbackId(Long feedbackId) {
        return feedbackResponseRepository.findAllFeedbackResponsesByFeedbackId(feedbackId);
    }

    @Override
    public List<FeedbackResponse> findAllFeedbackResponseByAuthorId(Long feedbackResponseAuthorId) {
        return null;
    }
}
