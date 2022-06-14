package com.alfa.testtask.delta_currency_checker.controller;



import com.alfa.testtask.delta_currency_checker.exception.IntegrationServiceException;
import com.alfa.testtask.delta_currency_checker.exception.NoExRatesException;
import com.alfa.testtask.delta_currency_checker.model.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.alfa.testtask.delta_currency_checker.common.ResponseStatus.*;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IntegrationServiceException.class)
    public ResponseEntity<CustomResponse> handleIntegrationServiceException(IntegrationServiceException ex) {

        return ResponseEntity.ok(CustomResponse.builder()
                                                .status(ERROR)
                                                .serviceName(ex.getServiceName())
                                                .message(ex.getMessage())
                                                .build());
    }

    @ExceptionHandler(NoExRatesException.class)
    public ResponseEntity<CustomResponse> handleNoExRatesException(NoExRatesException ex) {

        return ResponseEntity.ok(
                CustomResponse.builder()
                .status(ERROR)
                .message(ex.getMessage())
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleUnprocessedException(Exception ex) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                .status(ERROR)
                .message(ex.getMessage())
                .build()
        );
    }
}
