package com.coderone95.secu.exceptions;

import com.coderone95.secu.model.CustomResponse;
import com.coderone95.secu.model.ErrorDetails;
import com.coderone95.secu.model.ErrorResponse;
import com.coderone95.secu.model.Status;
import com.coderone95.secu.utility.Constants;
import org.json.simple.JSONObject;
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

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class GlobalExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> genericExceptionForQuestion(GenericException ex, WebRequest req){
//        ErrorResponse err = new ErrorResponse(ex.getMessage(),new Status("ERROR"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.STATUS,Constants.ERROR);
        jsonObject.put(Constants.MESSAGE,ex.getMessage());
        CustomResponse errorResponse = new CustomResponse(jsonObject);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
//        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.STATUS,Constants.ERROR);
        if(errorList.size() > 0){
            jsonObject.put(Constants.MESSAGE,errorList.get(0));
        }else{
            jsonObject.put(Constants.MESSAGE,ex.getMessage());
        }
        CustomResponse errorResponse = new CustomResponse(jsonObject);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

}
