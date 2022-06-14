package com.alfa.testtask.delta_currency_checker.model.giphy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GiphySearchResponse {
    List<Gif> data;
    MetaGiphy meta;
}
