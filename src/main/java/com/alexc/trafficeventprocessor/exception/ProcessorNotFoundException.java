package com.alexc.trafficeventprocessor.exception;

public class ProcessorNotFoundException extends RuntimeException {
    public ProcessorNotFoundException() {
    }

    public ProcessorNotFoundException(String message) {
        super(message);
    }

    public ProcessorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessorNotFoundException(Throwable cause) {
        super(cause);
    }
}
