package com.example.kolin.currencyconverterapp.data.model;

import java.util.List;

/**
 * Data class that represents search parameters
 */

public class SearchParam {
    // perod of search
    private int type;
    // time from what to search
    private long timeFrom;
    // time to
    private long timeTo;
    //particular currencies for history
    private List<Integer> checkedCurrencies;

    public SearchParam() {
    }

    public SearchParam(int type, long timeFrom, long timeTo, List<Integer> checkedCurrencies) {
        this.type = type;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.checkedCurrencies = checkedCurrencies;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }

    public List<Integer> getCheckedCurrencies() {
        return checkedCurrencies;
    }

    public void setCheckedCurrencies(List<Integer> checkedCurrencies) {
        this.checkedCurrencies = checkedCurrencies;
    }
}
