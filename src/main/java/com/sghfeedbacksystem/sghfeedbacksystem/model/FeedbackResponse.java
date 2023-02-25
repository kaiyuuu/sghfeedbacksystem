package com.sghfeedbacksystem.sghfeedbacksystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FeedbackResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackResponseId;
    @Column(nullable = false, length = 100)
    private String feedbackResponseTitle;
    @Column(nullable = false, length = 200)
    private String feedbackResponseBody;
    @Column(nullable = false)
    private LocalDateTime feedbackResponseDate;


    @ManyToOne
    @JoinColumn(name = "feedbackSubCategoryId")
    private Feedback feedback;

    public FeedbackResponse() {
    }

    public FeedbackResponse(String feedbackResponseTitle, String feedbackResponseBody, LocalDateTime feedbackResponseDate) {
        this.feedbackResponseTitle = feedbackResponseTitle;
        this.feedbackResponseBody = feedbackResponseBody;
        this.feedbackResponseDate = feedbackResponseDate;
    }

    public Long getFeedbackResponseId() {
        return feedbackResponseId;
    }

    public void setFeedbackResponseId(Long feedbackResponseId) {
        this.feedbackResponseId = feedbackResponseId;
    }

    public String getFeedbackResponseTitle() {
        return feedbackResponseTitle;
    }

    public void setFeedbackResponseTitle(String feedbackResponseTitle) {
        this.feedbackResponseTitle = feedbackResponseTitle;
    }

    public String getFeedbackResponseBody() {
        return feedbackResponseBody;
    }

    public void setFeedbackResponseBody(String feedbackResponseBody) {
        this.feedbackResponseBody = feedbackResponseBody;
    }

    public LocalDateTime getFeedbackResponseDate() {
        return feedbackResponseDate;
    }

    public void setFeedbackResponseDate(LocalDateTime feedbackResponseDate) {
        this.feedbackResponseDate = feedbackResponseDate;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
