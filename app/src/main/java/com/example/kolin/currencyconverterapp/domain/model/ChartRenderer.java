package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.ChartParam;
import com.example.kolin.currencyconverterapp.data.model.RatePojo;

import java.util.List;

/**
 * View model for chart data
 */

public class ChartRenderer {
    private boolean loading;
    private Throwable error;
    private ChartParam chartParam;
    private List<RatePojo> data;

    private ChartRenderer(List<RatePojo> data, ChartParam chartParam) {
        this.chartParam = chartParam;
        this.data = data;
        this.error = null;
        this.loading = false;
    }

    private ChartRenderer(Throwable error) {
        this.error = error;
        this.loading = false;
        this.data = null;
        this.chartParam = null;
    }

    private ChartRenderer(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.chartParam = null;
        this.data = null;
    }

    public static ChartRenderer getErrorObject(Throwable error) {
        return new ChartRenderer(error);
    }

    public static ChartRenderer getLoadingObject(boolean loading) {
        return new ChartRenderer(loading);
    }

    public static ChartRenderer getDataObject(List<RatePojo> data, ChartParam chartParam) {
        return new ChartRenderer(data, chartParam);
    }

    public boolean isLoading() {
        return loading;
    }

    public Throwable getError() {
        return error;
    }

    public ChartParam getChartParam() {
        return chartParam;
    }

    public List<RatePojo> getData() {
        return data;
    }
}
