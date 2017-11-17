package com.example.kolin.currencyconverterapp.data.db.dao;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import io.reactivex.Observable;

/**
 * Created by kolin on 17.11.2017.
 */

public interface HistoryCurrencyDAO extends DAO {

    void addHistory(int idCurrencyFrom,
                    int idCurrencyTo,
                    float sumFrom,
                    float sumTo,
                    float rate);

    Observable<CurrencyHistoryEntity> getHistory();

    Observable<CurrencyHistoryEntity> getHistory(long timeFrom, long timeTo);

    Observable<CurrencyHistoryEntity> getHistory(int idCurrency);

    Observable<CurrencyHistoryEntity> getHistory(int[] currencyIds);

    Observable<CurrencyHistoryEntity> getHistory(int[] currencyIds, long timeFrom, long timeTo);

    void clearHistory();

}
