package com.example.kolin.currencyconverterapp.data.model.entity;

/**
 * Created by kolin on 27.10.2017.
 */

public class CurrencyHistoryEntity {

    private int id;
    private int idCurrencyFrom;
    private int idCurrencyTo;
    private String currencyFrom;
    private String currencyTo;
    private int sumFrom;
    private int sumTo;
    private float rate;
    private long time;

    public CurrencyHistoryEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getIdCurrencyFrom() {
        return idCurrencyFrom;
    }

    public void setIdCurrencyFrom(int idCurrencyFrom) {
        this.idCurrencyFrom = idCurrencyFrom;
    }

    public int getIdCurrencyTo() {
        return idCurrencyTo;
    }

    public void setIdCurrencyTo(int idCurrencyTo) {
        this.idCurrencyTo = idCurrencyTo;
    }
}