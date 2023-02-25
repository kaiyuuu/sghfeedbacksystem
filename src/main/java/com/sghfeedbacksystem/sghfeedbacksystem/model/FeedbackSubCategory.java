package com.sghfeedbacksystem.sghfeedbacksystem.model;

import jakarta.persistence.*;

@Entity
public class FeedbackSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackSubCategoryId;
    @Column(nullable = false)
    private String subCategoryName;
    @Column(nullable = false)
    private String subCategoryDescription;

    @ManyToOne
    @JoinColumn(name = "feedbackCategoryId")
    private FeedbackCategory feedbackCategory;

    public FeedbackSubCategory() {
    }

    public FeedbackSubCategory(Long subCategoryId, String subCategoryName, String subCategoryDescription) {
        this.feedbackSubCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryDescription = subCategoryDescription;
    }

    public Long getSubCategoryId() {
        return feedbackSubCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.feedbackSubCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryDescription() {
        return subCategoryDescription;
    }

    public void setSubCategoryDescription(String subCategoryDescription) {
        this.subCategoryDescription = subCategoryDescription;
    }

    public FeedbackCategory getFeedbackCategory() {
        return feedbackCategory;
    }

    public void setFeedbackCategory(FeedbackCategory feedbackCategory) {
        this.feedbackCategory = feedbackCategory;
    }
}
