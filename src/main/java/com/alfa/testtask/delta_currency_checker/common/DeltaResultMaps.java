package com.alfa.testtask.delta_currency_checker.common;

import java.util.Map;

import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.INCREASED;
import static com.alfa.testtask.delta_currency_checker.common.DeltaResult.NOT_INCREASED;

public class DeltaResultMaps {


    public static final Map<String, String> DELTA_RESULT_MESSAGE_MAP = Map.of(
            INCREASED, "Exchange rate for %s is increased",
            NOT_INCREASED, "Exchange rate for %s is not increased"
    );

    public static final Map<String, String>  DELTA_RESULT_SEARCH_QUERY_MAP = Map.of(
            INCREASED, "rich",
            NOT_INCREASED, "broke"
    );

}
