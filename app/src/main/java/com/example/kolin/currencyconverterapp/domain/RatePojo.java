package com.example.kolin.currencyconverterapp.domain;

import java.util.HashMap;

/**
 * Created by kolin on 28.10.2017.
 */

public class RatePojo {

    private HashMap<String, Float> rates;

    public RatePojo() {
    }

    public HashMap<String, Float> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Float> rates) {
        this.rates = rates;
    }
}
