package com.alfa.testtask.delta_currency_checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.alfa.testtask.delta_currency_checker.client")
public class DeltaCurrencyCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeltaCurrencyCheckerApplication.class, args);
    }

}
