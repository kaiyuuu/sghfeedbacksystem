package com.sghfeedbacksystem.sghfeedbacksystem.dto;

public class FeedbackDTO {

    private Long userID;
    private String category;
    private String subcategory;
    private Boolean anonymity;
    private String title;
    private String feedback;
    //(userID : Long?, category : String, subcategory : String, anonymity : Boolean, title : String, feedback : String)


    public FeedbackDTO(Long userID, String category, String subcategory, Boolean anonymity, String title, String feedback) {
        this.userID = userID;
        this.category = category;
        this.subcategory = subcategory;
        this.anonymity = anonymity;
        this.title = title;
        this.feedback = feedback;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Boolean getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(Boolean anonymity) {
        this.anonymity = anonymity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
