package com.alfa.testtask.delta_currency_checker.model.giphy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public abstract class Image {
    private String url;
    private String width;
    private String height;
}
