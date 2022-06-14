package com.alfa.testtask.delta_currency_checker.api;

import com.alfa.testtask.delta_currency_checker.model.CustomResponse;

public interface DeltaCurrencyCheckerService {

    public CustomResponse checkDayDeltaForCurrency(String currencyCode);
}
