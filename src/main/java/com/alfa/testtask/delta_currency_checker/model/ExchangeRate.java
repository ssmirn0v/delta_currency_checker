package com.alfa.testtask.delta_currency_checker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ExchangeRate {
    String disclaimer;
    String licence;
    Long timestamp;
    String base;
    Map<String, BigDecimal> rates;
}
