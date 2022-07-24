package com.alexc.trafficeventprocessor.config;

import com.alexc.trafficeventprocessor.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ErrorDTO handleException(Exception exception) {
        log.error("Exception during request processing", exception);
        return new ErrorDTO("GENERAL_EXCEPTION", String.format("An exception occurred, see the log for more details: %s", exception.getMessage()));
    }
}
