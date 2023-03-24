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

    @Column(nullable = false)
    private String rejectionReason;


    @OneToOne
    @JoinColumn(name = "feedbackId")
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "feedbackResponseAuthor")
    private FeedbackTeam feedbackResponseAuthor;

    public FeedbackResponse() {
    }

    public FeedbackResponse(String feedbackResponseTitle, String feedbackResponseBody, LocalDateTime feedbackResponseDate) {
        this.feedbackResponseTitle = feedbackResponseTitle;
        this.feedbackResponseBody = feedbackResponseBody;
        this.feedbackResponseDate = feedbackResponseDate;
        this.rejectionReason = "";
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public FeedbackTeam getFeedbackResponseAuthor() {
        return feedbackResponseAuthor;
    }

    public void setFeedbackResponseAuthor(FeedbackTeam feedbackResponseAuthor) {
        this.feedbackResponseAuthor = feedbackResponseAuthor;
    }
}
