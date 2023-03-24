package com.sghfeedbacksystem.sghfeedbacksystem.dto;

public class ResponseBodyPublishStatusDTO {

    private String responseBody;

    private Boolean publishStatus;

    private String rejectionReason;

    public ResponseBodyPublishStatusDTO(String responseBody, Boolean isPublished, String rejectionReason) {
        this.responseBody = responseBody;
        this.publishStatus = isPublished;
        this.rejectionReason = rejectionReason;
    }

    public ResponseBodyPublishStatusDTO(String responseBody, Boolean publishStatus) {
        this.responseBody = responseBody;
        this.publishStatus = publishStatus;
        this.rejectionReason = "";
    }

    public ResponseBodyPublishStatusDTO() {
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Boolean getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Boolean published) {
        publishStatus = published;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
