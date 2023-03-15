package com.sghfeedbacksystem.sghfeedbacksystem.model;

import jakarta.persistence.*;

@Entity
public class FeedbackSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackSubCategoryId;
    @Column(nullable = false)
    private String feedbackSubcategoryName;
    @Column(nullable = false)
    private String feedbackSubCategoryDescription;

    @ManyToOne
    @JoinColumn(name = "feedbackCategoryId")
    private FeedbackCategory feedbackCategory;

    @OneToOne
    @JoinColumn(name = "feedbackSubCategoryPo")
    private FeedbackTeam feedbackSubCategoryPo;

    public FeedbackSubCategory() {
    }

    public FeedbackSubCategory(String feedbackSubcategoryName, String feedbackSubCategoryDescription) {
        this.feedbackSubcategoryName = feedbackSubcategoryName;
        this.feedbackSubCategoryDescription = feedbackSubCategoryDescription;
    }

    public Long getSubCategoryId() {
        return feedbackSubCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.feedbackSubCategoryId = subCategoryId;
    }

    public String getFeedbackSubcategoryName() {
        return feedbackSubcategoryName;
    }

    public void setFeedbackSubcategoryName(String feedbackSubcategoryName) {
        this.feedbackSubcategoryName = feedbackSubcategoryName;
    }

    public String getFeedbackSubCategoryDescription() {
        return feedbackSubCategoryDescription;
    }

    public void setFeedbackSubCategoryDescription(String feedbackSubCategoryDescription) {
        this.feedbackSubCategoryDescription = feedbackSubCategoryDescription;
    }

    public FeedbackCategory getFeedbackCategory() {
        return feedbackCategory;
    }

    public void setFeedbackCategory(FeedbackCategory feedbackCategory) {
        this.feedbackCategory = feedbackCategory;
    }

    public Long getFeedbackSubCategoryId() {
        return feedbackSubCategoryId;
    }

    public void setFeedbackSubCategoryId(Long feedbackSubCategoryId) {
        this.feedbackSubCategoryId = feedbackSubCategoryId;
    }

    public FeedbackTeam getFeedbackSubCategoryPo() {
        return feedbackSubCategoryPo;
    }

    public void setFeedbackSubCategoryPo(FeedbackTeam feedbackSubCategoryPo) {
        this.feedbackSubCategoryPo = feedbackSubCategoryPo;
    }
}
