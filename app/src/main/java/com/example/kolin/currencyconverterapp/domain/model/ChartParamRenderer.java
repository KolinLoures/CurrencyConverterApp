package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.ChartParam;

import java.util.List;

/**
 * View model for chart params
 */
public class ChartParamRenderer {

    private boolean loading;
    private Throwable error;
    private ChartParam chartParam;
    private List<String> data;

    private ChartParamRenderer(List<String> data, ChartParam chartParam) {
        this.chartParam = chartParam;
        this.data = data;
        this.error = null;
        this.loading = false;
    }

    private ChartParamRenderer(Throwable error) {
        this.error = error;
        this.loading = false;
        this.data = null;
        this.chartParam = null;
    }

    private ChartParamRenderer(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.chartParam = null;
        this.data = null;
    }

    public static ChartParamRenderer getErrorObject(Throwable error) {
        return new ChartParamRenderer(error);
    }

    public static ChartParamRenderer getLoadingObject(boolean loading) {
        return new ChartParamRenderer(loading);
    }

    public static ChartParamRenderer getDataObject(List<String> data, ChartParam chartParam) {
        return new ChartParamRenderer(data, chartParam);
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public ChartParam getChartParam() {
        return chartParam;
    }

    public void setChartParam(ChartParam chartParam) {
        this.chartParam = chartParam;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
