package org.ants.opportunity.model;

import org.springframework.http.HttpStatus;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ApiError {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
    Timestamp timestamp;
    private String timestampString;
    private HttpStatus status;
    private String message;
    private String debugMessage;

    private ApiError() {
        timestampString = this.simpleDateFormat.format(new Timestamp(System.currentTimeMillis()).getTime());
    }

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

    public HttpStatus getStatus() {
        return status;
    }

    public String getTimestamp() {
        return this.timestampString;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}

