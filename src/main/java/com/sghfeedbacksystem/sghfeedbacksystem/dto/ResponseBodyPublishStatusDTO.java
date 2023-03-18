package com.sghfeedbacksystem.sghfeedbacksystem.dto;

public class ResponseBodyPublishStatusDTO {

    private String responseBody;

    private Boolean publishStatus;

    public ResponseBodyPublishStatusDTO(String responseBody, Boolean isPublished) {
        this.responseBody = responseBody;
        this.publishStatus = isPublished;
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
}
