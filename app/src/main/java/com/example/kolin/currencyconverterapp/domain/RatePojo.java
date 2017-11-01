package com.example.kolin.currencyconverterapp.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kolin on 28.10.2017.
 */

public class RatePojo {

    @SerializedName("base")
    private String currencyFrom;
    private String currencyTo;
    private float rate;

    public RatePojo() {
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
