package com.alfa.testtask.delta_currency_checker.model.giphy;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class MetaGiphy {
    String msg;
    Integer status;
    String response_id;
}
