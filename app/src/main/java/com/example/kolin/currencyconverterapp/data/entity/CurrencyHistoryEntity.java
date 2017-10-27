package com.example.kolin.currencyconverterapp.data.entity;

/**
 * Created by kolin on 27.10.2017.
 */

public class CurrencyHistoryEntity {

    private int id;
    private CurrencyEntity currencyFrom;
    private CurrencyEntity currencyTo;
    private int sumFrom;
    private int sumTo;
    private float rate;
    private long time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyEntity getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(CurrencyEntity currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public CurrencyEntity getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(CurrencyEntity currencyTo) {
        this.currencyTo = currencyTo;
    }

    public int getSumFrom() {
        return sumFrom;
    }

    public void setSumFrom(int sumFrom) {
        this.sumFrom = sumFrom;
    }

    public int getSumTo() {
        return sumTo;
    }

    public void setSumTo(int sumTo) {
        this.sumTo = sumTo;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getTime() {
        return time;
    }
}
