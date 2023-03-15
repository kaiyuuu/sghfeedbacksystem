package com.sghfeedbacksystem.sghfeedbacksystem.model;

import com.sghfeedbacksystem.sghfeedbacksystem.util.enumeration.FeedbackStatusEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false, length = 100)
    private String feedbackTitle;
    @Column(nullable = false, length = 200)
    private String feedbackBody;
    @Column(nullable = false)
    private Boolean isAnonymous;
    @Column(nullable = false)
    private LocalDateTime feedbackDate;
    @Column(nullable = false)
    private FeedbackStatusEnum feedbackStatus;
    @Column(nullable = false)
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "feedbackSubCategory")
    private FeedbackSubCategory feedbackSubCategory;

    @ManyToOne
    @JoinColumn(name = "feedbackAuthor")
    private Staff feedbackAuthor;

    public Feedback() {
    }

    public Feedback(String feedbackTitle, String feedbackBody, Boolean isAnonymous, LocalDateTime feedbackDate, FeedbackStatusEnum feedbackStatus) {
        this.feedbackTitle = feedbackTitle;
        this.feedbackBody = feedbackBody;
        this.isAnonymous = isAnonymous;
        this.feedbackDate = feedbackDate;
        this.feedbackStatus = feedbackStatus;
        this.isPublished = false;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackBody() {
        return feedbackBody;
    }

    public void setFeedbackBody(String feedbackBody) {
        this.feedbackBody = feedbackBody;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public LocalDateTime getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(LocalDateTime feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public FeedbackStatusEnum getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(FeedbackStatusEnum feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public FeedbackSubCategory getFeedbackSubCategory() {
        return feedbackSubCategory;
    }

    public void setFeedbackSubCategory(FeedbackSubCategory feedbackSubCategory) {
        this.feedbackSubCategory = feedbackSubCategory;
    }

    public Staff getFeedbackAuthor() {
        return feedbackAuthor;
    }

    public void setFeedbackAuthor(Staff feedbackAuthor) {
        this.feedbackAuthor = feedbackAuthor;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }


}
