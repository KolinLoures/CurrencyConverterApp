package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 05.12.2017.
 */

public class GetNames {

    private DAO queries;

    public GetNames() {
        this.queries = new DataBaseQueries();
    }

    protected Observable<List<String>> createObservable(GetNamesParams params) {
        return queries.getNames();
    }

    public static class GetNamesParams {

    }
}
