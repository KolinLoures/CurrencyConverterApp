package com.example.kolin.currencyconverterapp.data.model;

import java.util.List;

/**
 * Created by kolin on 10.12.2017.
 */

public class SearchParam {
    private int type;
    private long timeFrom;
    private long timeTo;
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
