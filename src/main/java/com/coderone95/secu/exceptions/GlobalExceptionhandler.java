package com.coderone95.secu.exceptions;

import com.coderone95.secu.model.ErrorDetails;
import com.coderone95.secu.model.ErrorResponse;
import com.coderone95.secu.model.Status;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class GlobalExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> genericExceptionForQuestion(GenericException ex, WebRequest req){
        ErrorResponse err = new ErrorResponse(ex.getMessage(),new Status("ERROR"));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
