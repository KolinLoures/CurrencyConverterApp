package com.example.kolin.currencyconverterapp.domain.model;

import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;

import java.util.List;

/**
 * View model for search params
 */

public class SearchParamsRenderer {

    private boolean loading;
    private Throwable error;
    private SearchParam searchParam;
    private List<CurrencyEntity> data;

    private SearchParamsRenderer(List<CurrencyEntity> data, SearchParam searchParam) {
        this.searchParam = searchParam;
        this.data = data;
        this.error = null;
        this.loading = false;
    }

    private SearchParamsRenderer(Throwable error) {
        this.error = error;
        this.loading = false;
        this.data = null;
        this.searchParam = null;
    }

    private SearchParamsRenderer(boolean loading) {
        this.loading = loading;
        this.error = null;
        this.searchParam = null;
        this.data = null;
    }

    public static SearchParamsRenderer getErrorObject(Throwable error) {
        return new SearchParamsRenderer(error);
    }

    public static SearchParamsRenderer getLoadingObject(boolean loading) {
        return new SearchParamsRenderer(loading);
    }

    public static SearchParamsRenderer getDataObject(List<CurrencyEntity> data, SearchParam searchParam) {
        return new SearchParamsRenderer(data, searchParam);
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

    public SearchParam getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(SearchParam searchParam) {
        this.searchParam = searchParam;
    }

    public List<CurrencyEntity> getData() {
        return data;
    }

    public void setData(List<CurrencyEntity> data) {
        this.data = data;
    }
}
