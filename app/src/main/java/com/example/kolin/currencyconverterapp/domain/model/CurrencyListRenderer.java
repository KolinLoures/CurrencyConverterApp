package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;

import java.util.List;

/**
 * View model for currency list
 */

public class CurrencyListRenderer {

    private boolean loading;
    private Throwable error;
    private List<CurrencyEntity> data;

    private CurrencyListRenderer(Throwable error) {
        this.error = error;
        this.loading = false;
        this.data = null;
    }

    private CurrencyListRenderer(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.data = null;
    }

    private CurrencyListRenderer(List<CurrencyEntity> data) {
        this.data = data;
        this.loading = false;
        this.error = null;
    }

    public static CurrencyListRenderer getErrorObject(Throwable error) {
        return new CurrencyListRenderer(error);
    }

    public static CurrencyListRenderer getLoadingObject(boolean loading) {
        return new CurrencyListRenderer(loading);
    }

    public static CurrencyListRenderer getDataObject(List<CurrencyEntity> data) {
        return new CurrencyListRenderer(data);
    }

    public boolean isLoading() {
        return loading;
    }

    public Throwable getError() {
        return error;
    }

    public List<CurrencyEntity> getData() {
        return data;
    }
}
