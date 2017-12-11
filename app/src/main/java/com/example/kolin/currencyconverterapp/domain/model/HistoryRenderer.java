package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.List;

/**
 * Created by kolin on 10.12.2017.
 */

public class HistoryRenderer {

    private boolean loading;
    private Throwable error;
    private SearchParam searchParam;
    private List<CurrencyHistoryEntity> data;

    private HistoryRenderer(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.data = null;
    }

    private HistoryRenderer(Throwable error){
        this.loading = false;
        this.error = error;
        this.data = null;
    }

    private HistoryRenderer(List<CurrencyHistoryEntity> data, SearchParam searchParam){
        this.loading = false;
        this.error = null;
        this.data = data;
        this.searchParam = searchParam;
    }

    public static HistoryRenderer getErrorObject(Throwable error) {
        return new HistoryRenderer(error);
    }

    public static HistoryRenderer getLoadingObject(boolean loading) {
        return new HistoryRenderer(loading);
    }

    public static HistoryRenderer getDataObject(List<CurrencyHistoryEntity> data, SearchParam searchParam) {
        return new HistoryRenderer(data, searchParam);
    }

    public boolean isLoading() {
        return loading;
    }

    public Throwable getError() {
        return error;
    }

    public List<CurrencyHistoryEntity> getData() {
        return data;
    }

    public SearchParam getSearchParam() {
        return searchParam;
    }
}
