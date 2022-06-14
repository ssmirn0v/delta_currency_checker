package com.alfa.testtask.delta_currency_checker.service.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomIntGenerator {
    public int getRandomInt(int start, int finish) {
        return ThreadLocalRandom.current().nextInt(start, finish);
    }
}
