package com.example.kolin.currencyconverterapp.data.model;

import java.util.List;

/**
 * Pojo class to get currencies name from cloud
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
