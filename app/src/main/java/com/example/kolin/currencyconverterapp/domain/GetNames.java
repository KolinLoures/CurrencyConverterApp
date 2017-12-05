package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.db.dao.DAO;
import com.example.kolin.currencyconverterapp.data.db.dao.DataBaseQueries;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 05.12.2017.
 */

public class GetNames extends BaseObservableUseCase<List<String>, GetNames.GetNamesParams> {

    private DAO queries;

    public GetNames() {
        this.queries = DataBaseQueries.getInstance();
    }

    @Override
    protected Observable<List<String>> createObservable(GetNamesParams params) {
        return queries.getNames();
    }

    class GetNamesParams {

    }
}
