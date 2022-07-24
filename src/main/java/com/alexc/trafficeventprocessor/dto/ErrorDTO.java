package com.alexc.trafficeventprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorDTO {
    private String errorCode;
    private String errorMessage;
}
