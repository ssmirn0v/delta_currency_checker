package com.alfa.testtask.delta_currency_checker.controller;

import com.alfa.testtask.delta_currency_checker.api.DeltaCurrencyCheckerService;
import com.alfa.testtask.delta_currency_checker.client.GiphyClient;
import com.alfa.testtask.delta_currency_checker.client.OERClient;
import com.alfa.testtask.delta_currency_checker.controller.htmlResponse.HtmlResponseBuilder;
import com.alfa.testtask.delta_currency_checker.model.CustomResponse;
import com.alfa.testtask.delta_currency_checker.model.ExchangeRate;
import com.alfa.testtask.delta_currency_checker.model.giphy.GiphySearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckCurrencyController {

    @Autowired
    OERClient oerClient;

    @Autowired
    GiphyClient giphyClient;

    @Autowired
    DeltaCurrencyCheckerService deltaCheckerService;

    @GetMapping("/api/checkDelta/{currencyCode}")
    public ResponseEntity<CustomResponse> checkDeltaCurrency(@PathVariable String currencyCode) {
        return ResponseEntity.ok(deltaCheckerService.checkDayDeltaForCurrency(currencyCode));
    }

    @GetMapping("/checkDelta/{currencyCode}")
    public String checkDeltaCurrencyHtml(@PathVariable String currencyCode) {
        CustomResponse deltaCheckerResult = deltaCheckerService.checkDayDeltaForCurrency(currencyCode);
        return HtmlResponseBuilder.buildSuccessfulResponse(deltaCheckerResult.getMessage(), deltaCheckerResult.getGif());
    }

}
