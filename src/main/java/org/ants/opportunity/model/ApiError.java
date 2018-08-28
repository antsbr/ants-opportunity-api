package org.ants.opportunity.model;

import org.ants.opportunity.config.TimeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiError {

    @Autowired
    private TimeConfig timeConfig;
    private HttpStatus status;
    private String message;
    private String debugMessage;

    private ApiError() {}

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timeConfig.getTimestamp();
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}

