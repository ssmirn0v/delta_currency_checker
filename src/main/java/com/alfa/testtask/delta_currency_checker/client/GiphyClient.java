package com.alfa.testtask.delta_currency_checker.client;

import com.alfa.testtask.delta_currency_checker.model.giphy.GiphySearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.giphy.name}", url = "${feign.giphy.url}")
public interface GiphyClient {

    @GetMapping("gifs/search")
    public GiphySearchResponse searchGif(@RequestParam String q, @RequestParam Integer offset);
}
