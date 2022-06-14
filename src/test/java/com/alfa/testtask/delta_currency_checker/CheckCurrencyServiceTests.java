package com.alfa.testtask.delta_currency_checker;


import com.alfa.testtask.delta_currency_checker.api.DeltaCurrencyCheckerService;
import com.alfa.testtask.delta_currency_checker.client.GiphyClient;
import com.alfa.testtask.delta_currency_checker.client.OERClient;
import com.alfa.testtask.delta_currency_checker.exception.NoExRatesException;
import com.alfa.testtask.delta_currency_checker.model.CustomResponse;
import com.alfa.testtask.delta_currency_checker.model.ExchangeRate;
import com.alfa.testtask.delta_currency_checker.model.giphy.*;
import com.alfa.testtask.delta_currency_checker.service.util.RandomIntGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static com.alfa.testtask.delta_currency_checker.common.ResponseStatus.*;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResultMaps.DELTA_RESULT_MESSAGE_MAP;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.INCREASED;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.NOT_INCREASED;
import static com.alfa.testtask.delta_currency_checker.common.Errors.NO_EX_RATES_FOUND;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CheckCurrencyServiceTests {

    @MockBean
    GiphyClient mockGiphyClient;

    @MockBean
    OERClient mockOerClient;

    @MockBean
    RandomIntGenerator randomIntGenerator;



    @Autowired
    DeltaCurrencyCheckerService deltaCurrencyCheckerService;

    @BeforeEach
    void init() {
        when(randomIntGenerator.getRandomInt(0, 5000)).thenReturn(5);
    }


    @Test
    void testCheckingForDeltaCurrency_SuccessStatus_ExchangeRateIsIncreased() {
        String currencyCode = "RUB";
        String date = LocalDate.now().minusDays(1).toString();

        BigDecimal todayExRateValue = BigDecimal.valueOf(55.234123);
        BigDecimal yesterdayExRateValue = BigDecimal.valueOf(57.624893);


        when(mockOerClient.getLatestExchangeRate(currencyCode)).thenReturn(ExchangeRate.builder()
        .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
        .licence("https://openexchangerates.org/license")
        .timestamp(1655078394L)
        .base("USD")
        .rates(Map.of(currencyCode, todayExRateValue))
                .build());

        when(mockOerClient.getExchangeRateForDate(date, currencyCode)).thenReturn(ExchangeRate.builder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .licence("https://openexchangerates.org/license")
                .timestamp(1655078394L)
                .base("USD")
                .rates(Map.of(currencyCode, yesterdayExRateValue))
                .build());

        Gif gif = Gif.builder()
                .id("3oFzlWFLgj0t6LhEyY")
                .images(
                        Images.builder()
                                .fixed_height(FixedHeightGif.builder()
                                        .height("200")
                                        .width("267")
                                        .url("https://media4.giphy.com/media/3oFzlWFLgj0t6LhEyY/200.gif?cid=884e5cfe5ey77ehdnooqqcydk7lv33v348rb4qdusvw4ftmk&rid=200.gif&ct=g")
                                        .build())
                                .build()
                )
                .slug("golden-age-3oFzlWFLgj0t6LhEyY")
                .source("")
                .url("https://giphy.com/gifs/golden-age-3oFzlWFLgj0t6LhEy")
                .type("gif")
                .build();

        when(mockGiphyClient.searchGif("rich", 5)).thenReturn(GiphySearchResponse.builder()
        .data(Collections.singletonList(gif))
        .meta(MetaGiphy.builder()
                .msg("OK")
                .status(200)
                .response_id("5ey77ehdnooqqcydk7lv33v348rb4qdusvw4ftmk")
                .build())
        .build());


        CustomResponse deltaCheckResult = deltaCurrencyCheckerService.checkDayDeltaForCurrency(currencyCode);

        Assertions.assertEquals(deltaCheckResult, CustomResponse.builder()
                                                    .status(SUCCESS)
                                                    .gif(gif.getImages().getFixed_height().getUrl())
                                                    .message(String.format(DELTA_RESULT_MESSAGE_MAP.get(INCREASED), currencyCode))
                                                    .todayExRate(todayExRateValue.toString())
                                                    .yesterdayExRate(yesterdayExRateValue.toString())
                                                    .build());

    }


    @Test
    void testCheckingForDeltaCurrency_SuccessStatus_ExchangeRateIsNotIncreased() {
        String currencyCode = "RUB";
        String date = LocalDate.now().minusDays(1).toString();

        BigDecimal todayExRateValue = BigDecimal.valueOf(58);
        BigDecimal yesterdayExRateValue = BigDecimal.valueOf(55);


        when(mockOerClient.getLatestExchangeRate(currencyCode)).thenReturn(ExchangeRate.builder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .licence("https://openexchangerates.org/license")
                .timestamp(1655078394L)
                .base("USD")
                .rates(Map.of(currencyCode, todayExRateValue))
                .build());

        when(mockOerClient.getExchangeRateForDate(date, currencyCode)).thenReturn(ExchangeRate.builder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .licence("https://openexchangerates.org/license")
                .timestamp(1655078394L)
                .base("USD")
                .rates(Map.of(currencyCode, yesterdayExRateValue))
                .build());

        Gif gif = Gif.builder()
                .id("3oFzlWFLgj0t6LhEyY")
                .images(
                        Images.builder()
                                .fixed_height(FixedHeightGif.builder()
                                        .height("200")
                                        .width("267")
                                        .url("https://media4.giphy.com/media/3oFzlWFLgj0t6LhEyY/200.gif?cid=884e5cfe5ey77ehdnooqqcydk7lv33v348rb4qdusvw4ftmk&rid=200.gif&ct=g")
                                        .build())
                                .build()
                )
                .slug("golden-age-3oFzlWFLgj0t6LhEyY")
                .source("")
                .url("https://giphy.com/gifs/golden-age-3oFzlWFLgj0t6LhEy")
                .type("gif")
                .build();

        when(mockGiphyClient.searchGif("broke", 5)).thenReturn(GiphySearchResponse.builder()
                .data(Collections.singletonList(gif))
                .meta(MetaGiphy.builder()
                        .msg("OK")
                        .status(200)
                        .response_id("5ey77ehdnooqqcydk7lv33v348rb4qdusvw4ftmk")
                        .build())
                .build());


        CustomResponse deltaCheckResult = deltaCurrencyCheckerService.checkDayDeltaForCurrency(currencyCode);

        Assertions.assertEquals(deltaCheckResult, CustomResponse.builder()
                .status(SUCCESS)
                .gif(gif.getImages().getFixed_height().getUrl())
                .message(String.format(DELTA_RESULT_MESSAGE_MAP.get(NOT_INCREASED), currencyCode))
                .todayExRate(todayExRateValue.toString())
                .yesterdayExRate(yesterdayExRateValue.toString())
                .build());

    }


    @Test
    void testCheckingForDeltaCurrency_ErrorStatus_NoExRatesFoundForCode() {
        String currencyCode = "ZZZZ";
        String date = LocalDate.now().minusDays(1).toString();

        BigDecimal todayExRateValue = BigDecimal.valueOf(55.234123);
        BigDecimal yesterdayExRateValue = BigDecimal.valueOf(57.624893);


        when(mockOerClient.getLatestExchangeRate(currencyCode)).thenReturn(ExchangeRate.builder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .licence("https://openexchangerates.org/license")
                .timestamp(1655078394L)
                .base("USD")
                .rates(new HashMap<>())
                .build());

        when(mockOerClient.getExchangeRateForDate(date, currencyCode)).thenReturn(ExchangeRate.builder()
                .disclaimer("Usage subject to terms: https://openexchangerates.org/terms")
                .licence("https://openexchangerates.org/license")
                .timestamp(1655078394L)
                .base("USD")
                .rates(new HashMap<>())
                .build());



        Exception exception = Assertions.assertThrows(NoExRatesException.class, () -> {
                CustomResponse deltaCheckResult = deltaCurrencyCheckerService.checkDayDeltaForCurrency(currencyCode);
        });

        String expectedErrorMessage = String.format(NO_EX_RATES_FOUND, currencyCode);

        Assert.isTrue(exception.getMessage().contains(expectedErrorMessage), "NO_EX_RATES_FOUND message should be within thrown exception");

    }



}
