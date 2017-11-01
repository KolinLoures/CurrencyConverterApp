package com.example.kolin.currencyconverterapp.domain;

import java.util.List;

/**
 * Created by kolin on 01.11.2017.
 */

public class SupportCurrencies {

    private List<String> listCurrencies;

    public SupportCurrencies() {
    }

    public List<String> getListCurrencies() {
        return listCurrencies;
    }

    public void setListCurrencies(List<String> listCurrencies) {
        this.listCurrencies = listCurrencies;
    }
}
