package com.example.kolin.currencyconverterapp.data.model;

/**
 * POJO class for rate
 */

public class RatePojo {

    private String currencyFrom;
    private String currencyTo;
    private String date;
    private float rate;

    private boolean isFromCache = false;

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

    public boolean isFromCache() {
        return isFromCache;
    }

    public void setFromCache(boolean fromCache) {
        isFromCache = fromCache;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RatePojo{" +
                "currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", rate=" + rate +
                ", isFromCache=" + isFromCache +
                '}';
    }
}
