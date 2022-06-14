package com.alfa.testtask.delta_currency_checker.model.giphy;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonRootName("fixed_height")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class FixedHeightGif extends Image {

    public FixedHeightGif(String url, String width, String height) {
        super(url, width, height);
    }
}
