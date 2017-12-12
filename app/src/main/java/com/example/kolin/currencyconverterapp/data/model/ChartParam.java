package com.example.kolin.currencyconverterapp.data.model;

/**
 * Created by kolin on 11.12.2017.
 */

public class ChartParam {

    private String currFrom;
    private String currTo;
    private int period;

    public ChartParam(String currFrom, String currTo, int period) {
        this.currFrom = currFrom;
        this.currTo = currTo;
        this.period = period;
    }

    public ChartParam() {
    }

    public String getCurrFrom() {
        return currFrom;
    }

    public void setCurrFrom(String currFrom) {
        this.currFrom = currFrom;
    }

    public String getCurrTo() {
        return currTo;
    }

    public void setCurrTo(String currTo) {
        this.currTo = currTo;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
