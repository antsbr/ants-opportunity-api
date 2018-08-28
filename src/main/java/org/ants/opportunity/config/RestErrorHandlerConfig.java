package org.ants.opportunity.config;

import org.ants.opportunity.exception.OpportunityTypeNotFoundException;
import org.ants.opportunity.model.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestErrorHandlerConfig extends ResponseEntityExceptionHandler {
    @Autowired
    private ApiError apiError;

    @ExceptionHandler(OpportunityTypeNotFoundException.class)
    protected ResponseEntity<Object> handleOpportunityTypeNotFoundException(
            OpportunityTypeNotFoundException ex) {
        apiError.setStatus(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage("Please find a valid type in /api/type");
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
