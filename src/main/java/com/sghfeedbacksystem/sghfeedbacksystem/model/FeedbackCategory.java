package com.sghfeedbacksystem.sghfeedbacksystem.model;

import jakarta.persistence.*;

@Entity
public class FeedbackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackCategoryId;
    @Column(nullable = false)
    private String feedbackCategoryName;
    @Column(nullable = false)
    private String feedbackCategoryDescription;


    public FeedbackCategory() {
    }

    public FeedbackCategory(String feedbackCategoryName, String feedbackCategoryDescription) {
        this.feedbackCategoryName = feedbackCategoryName;
        this.feedbackCategoryDescription = feedbackCategoryDescription;
    }

    public Long getFeedbackCategoryId() {
        return feedbackCategoryId;
    }

    public void setFeedbackCategoryId(Long feedbackCategoryId) {
        this.feedbackCategoryId = feedbackCategoryId;
    }

    public String getFeedbackCategoryName() {
        return feedbackCategoryName;
    }

    public void setFeedbackCategoryName(String feedbackCategoryName) {
        this.feedbackCategoryName = feedbackCategoryName;
    }

    public String getFeedbackCategoryDescription() {
        return feedbackCategoryDescription;
    }

    public void setFeedbackCategoryDescription(String feedbackCategoryDescription) {
        this.feedbackCategoryDescription = feedbackCategoryDescription;
    }
}
