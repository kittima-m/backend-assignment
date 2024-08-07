package com.ata.salary.exception;

import com.ata.salary.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class, NumberFormatException.class, PathElementException.class, IllegalArgumentException.class, PropertyReferenceException.class})
    public ResponseEntity<ErrorResponse> handleGenericErrorExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage("Bad Request: some parameter is in incorrect format. -> " + ex.getMessage())
                .timeStamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
