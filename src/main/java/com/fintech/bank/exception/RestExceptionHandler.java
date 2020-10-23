package com.fintech.bank.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccountNotFoundException.class, CustomerNotFoundException.class, PayeeNotFoundException.class,
            TransactionNotFoundException.class, EmployeeNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(
            Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage(ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler({InvalidTransactionException.class, InvalidPayeeException.class, InvalidAccountException.class})
    protected ResponseEntity<Object> handleInvalidOperation(
            Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}