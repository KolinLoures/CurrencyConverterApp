package com.example.kolin.currencyconverterapp.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kolin on 28.10.2017.
 */

public class ResponsePojo {

    @SerializedName("base")
    private String fromCurrency;
    @SerializedName("rates")
    private RatePojo toCurrency;

    public ResponsePojo() {
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public RatePojo getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(RatePojo toCurrency) {
        this.toCurrency = toCurrency;
    }
}
