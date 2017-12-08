package com.example.kolin.currencyconverterapp.data.model;

import java.util.List;

/**
 * Created by kolin on 01.11.2017.
 */

public class SupportCurrenciesPojo {

    private List<String> listCurrencies;

    public SupportCurrenciesPojo() {
    }

    public List<String> getListCurrencies() {
        return listCurrencies;
    }

    public void setListCurrencies(List<String> listCurrencies) {
        this.listCurrencies = listCurrencies;
    }
}
