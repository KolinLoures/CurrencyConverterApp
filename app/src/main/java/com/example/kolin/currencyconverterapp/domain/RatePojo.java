package com.example.kolin.currencyconverterapp.domain;

/**
 * Created by kolin on 28.10.2017.
 */

public class RatePojo {

    private String name;
    private float rate;

    public RatePojo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
