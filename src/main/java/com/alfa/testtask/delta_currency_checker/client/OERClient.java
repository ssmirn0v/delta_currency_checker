package com.alfa.testtask.delta_currency_checker.client;

import com.alfa.testtask.delta_currency_checker.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.oer.name}", url = "${feign.oer.url}")
public interface OERClient {

    @GetMapping("/latest.json")
    public ExchangeRate getLatestExchangeRate(@RequestParam String symbols);

    @GetMapping("/historical/{date}.json")
    public ExchangeRate getExchangeRateForDate(@PathVariable String date, @RequestParam String symbols);

}
