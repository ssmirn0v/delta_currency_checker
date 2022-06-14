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
public class Gif {
    private String type;
    private String id;
    private String url;
    private String slug;
    private String source;
    private Images images;

}
