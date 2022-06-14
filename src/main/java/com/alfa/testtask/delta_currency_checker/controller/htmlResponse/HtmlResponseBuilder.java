package com.alfa.testtask.delta_currency_checker.controller.htmlResponse;

public class HtmlResponseBuilder {

    public static String buildSuccessfulResponse(String msg, String gifUrl) {
        return String.format("<h1>%s</h1>\n" +
                "<img src=\"%s\"></img>", msg, gifUrl);
    }
}
