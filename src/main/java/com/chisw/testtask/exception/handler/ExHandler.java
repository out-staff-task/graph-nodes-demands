package com.chisw.testtask.exception.handler;

import com.chisw.testtask.exception.DataRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataRequestException.class)
    public String handleDatabaseException(DataRequestException e) {
        log.error(e.getMessage());
        return "{\"error\":\"" + e.getMessage() + "\"}";
    }
}
