package com.example.kolin.currencyconverterapp.data.model.entity;

/**
 * Data class of history
 */

public class CurrencyHistoryEntity {

    //id of history cahnge
    private int id;
    //id of currency from
    private int idCurrencyFrom;
    //id of currency to
    private int idCurrencyTo;
    //name of currency from
    private String currencyFrom;
    //name of currency to
    private String currencyTo;
    //sum from
    private int sumFrom;
    //sum to
    private int sumTo;
    //rate of change
    private float rate;
    // timw of change
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
