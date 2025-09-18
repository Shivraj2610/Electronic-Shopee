package com.shiv.electronic.store.exceptions;


import com.shiv.electronic.store.custome.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Handle Resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(
            ResourceNotFoundException ex
    ){
        logger.info("Exception Handler invoked");
        ApiResponseMessage response = new ApiResponseMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                true
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response=new HashMap<>();
        allErrors.forEach(
                objectError -> {
                    //Get Message
                    String message = objectError.getDefaultMessage();
                    //Get Field
                    String field = ((FieldError) objectError).getField();
                    //store field as key and message as value in Map
                    response.put(field,message);
                });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
