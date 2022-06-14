package com.alfa.testtask.delta_currency_checker.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
public class CustomResponse {
    String message;
    String gif;
    String status;
    String serviceName;
    String yesterdayExRate;
    String todayExRate;
}
