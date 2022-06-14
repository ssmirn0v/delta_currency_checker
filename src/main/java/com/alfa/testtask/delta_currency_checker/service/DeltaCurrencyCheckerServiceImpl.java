package com.alfa.testtask.delta_currency_checker.service;

import com.alfa.testtask.delta_currency_checker.api.DeltaCurrencyCheckerService;
import com.alfa.testtask.delta_currency_checker.client.GiphyClient;
import com.alfa.testtask.delta_currency_checker.client.OERClient;
import com.alfa.testtask.delta_currency_checker.exception.IntegrationServiceException;
import com.alfa.testtask.delta_currency_checker.exception.NoExRatesException;
import com.alfa.testtask.delta_currency_checker.model.CustomResponse;
import com.alfa.testtask.delta_currency_checker.model.ExchangeRate;
import com.alfa.testtask.delta_currency_checker.model.giphy.GiphySearchResponse;
import com.alfa.testtask.delta_currency_checker.service.util.RandomIntGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.NOT_INCREASED;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResultMaps.DELTA_RESULT_MESSAGE_MAP;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResultMaps.DELTA_RESULT_SEARCH_QUERY_MAP;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.INCREASED;
import static com.alfa.testtask.delta_currency_checker.common.IntegrationServices.GIPHY;
import static com.alfa.testtask.delta_currency_checker.common.IntegrationServices.OER;
import static com.alfa.testtask.delta_currency_checker.common.ResponseStatus.*;
import static com.alfa.testtask.delta_currency_checker.common.Errors.NO_EX_RATES_FOUND;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class DeltaCurrencyCheckerServiceImpl implements DeltaCurrencyCheckerService {

    @Autowired
    GiphyClient giphyClient;

    @Autowired
    OERClient oerClient;

    @Autowired
    RandomIntGenerator randomIntGenerator;


    @Override
    public CustomResponse checkDayDeltaForCurrency(String currencyCode) {
        ExchangeRate todayExRate = oerClient.getLatestExchangeRate(currencyCode);
        ExchangeRate yesterdayExRate = oerClient.getExchangeRateForDate(LocalDate.now()
                                                                    .minusDays(1).toString(), currencyCode);


        if (isRateIncreased(currencyCode, todayExRate, yesterdayExRate)) {

            GiphySearchResponse giphy = processRequestToGiphy(DELTA_RESULT_SEARCH_QUERY_MAP.get(INCREASED));
            return CustomResponse.builder()
                    .gif(giphy.getData().get(0).getImages().getFixed_height().getUrl())
                    .message(String.format(DELTA_RESULT_MESSAGE_MAP.get(INCREASED), currencyCode))
                    .status(SUCCESS)
                    .todayExRate(todayExRate.getRates().get(currencyCode).toString())
                    .yesterdayExRate(yesterdayExRate.getRates().get(currencyCode).toString())
                    .build();
        } else {
            GiphySearchResponse giphy = processRequestToGiphy(DELTA_RESULT_SEARCH_QUERY_MAP.get(NOT_INCREASED));
            return CustomResponse.builder()
                    .gif(giphy.getData().get(0).getImages().getFixed_height().getUrl())
                    .message(String.format(DELTA_RESULT_MESSAGE_MAP.get(NOT_INCREASED), currencyCode))
                    .status(SUCCESS)
                    .todayExRate(todayExRate.getRates().get(currencyCode).toString())
                    .yesterdayExRate(yesterdayExRate.getRates().get(currencyCode).toString())
                    .build();
        }


    }

    private GiphySearchResponse processRequestToGiphy(String searchQuery) {
        int randomOffset = randomIntGenerator.getRandomInt(0, 5000);
        GiphySearchResponse giphyResponse = giphyClient.searchGif(searchQuery, randomOffset);
        if (!HttpStatus.valueOf(giphyResponse.getMeta().getStatus()).isError()) {
            return giphyResponse;
        } else {
            throw new IntegrationServiceException(GIPHY, giphyResponse.getMeta().getMsg());
        }
    }

    public boolean isRateIncreased(String currencyCode, ExchangeRate todayExRate, ExchangeRate yesterdayExRate) {
        BigDecimal todayExRateValue = BigDecimal.ZERO;
        BigDecimal yesterdayExRateValue = BigDecimal.ZERO;


        if (todayExRate.getRates().containsKey(currencyCode)) {
            todayExRateValue = todayExRate.getRates().get(currencyCode);
        } else {
            throw new NoExRatesException(String.format(NO_EX_RATES_FOUND, currencyCode));
        }
        if (yesterdayExRate.getRates().containsKey(currencyCode)) {
            yesterdayExRateValue = yesterdayExRate.getRates().get(currencyCode);
        } else {
            throw new NoExRatesException(String.format(NO_EX_RATES_FOUND, currencyCode));
        }

        return todayExRateValue.compareTo(yesterdayExRateValue) == -1;
    }

}
