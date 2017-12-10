package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.RatePojo;

/**
 * Created by kolin on 09.12.2017.
 */

public class ConverterRateRender {
    private boolean loading;
    private Throwable error;
    private float result;
    private boolean reverse;
    private RatePojo data;
    private boolean isRateExpired;

    private ConverterRateRender(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.data = null;
    }

    private ConverterRateRender(Throwable error){
        this.loading = false;
        this.error = error;
        this.data = null;
    }

    private ConverterRateRender(RatePojo data, float result, boolean reverse, boolean isRateExpired) {
        this.loading = false;
        this.error = null;
        this.result = result;
        this.reverse = reverse;
        this.data = data;
        this.isRateExpired = isRateExpired;
    }

    public static ConverterRateRender getErrorObject(Throwable error) {
        return new ConverterRateRender(error);
    }

    public static ConverterRateRender getLoadingObject(boolean loading) {
        return new ConverterRateRender(loading);
    }

    public static ConverterRateRender getDataObject(RatePojo data, float result, boolean reverse, boolean isRateExpired) {
        return new ConverterRateRender(data, result, reverse, isRateExpired);
    }

    public boolean isLoading() {
        return loading;
    }

    public Throwable getError() {
        return error;
    }

    public RatePojo getData() {
        return data;
    }

    public float getResult() {
        return result;
    }

    public boolean isReverse() {
        return reverse;
    }

    public boolean isRateExpired() {
        return isRateExpired;
    }
}
