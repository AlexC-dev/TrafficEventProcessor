package com.alexc.trafficeventprocessor.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FinesSummaryDTO {
    int paidFines;
    int unpaidFines;
}
