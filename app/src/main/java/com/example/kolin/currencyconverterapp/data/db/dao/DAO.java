package com.example.kolin.currencyconverterapp.data.db.dao;

import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.10.2017.
 */

public interface DAO {

    Observable<CurrencyEntity> getAllCurrency();

    Observable<CurrencyEntity> getAllFavoriteCurrency();

    Observable<List<String>> getNames();

    void addCurrency(String name);

    void addCurrency(List<String> names);

    void addCurrencyToFavorite(int id);

    void removeCurrencyFromFavorite(int id);

    void updateCurrencyTime(int id);

    void addHistory(int idCurrencyFrom,
                    int idCurrencyTo,
                    float sumFrom,
                    float sumTo,
                    float rate);

    Observable<List<CurrencyHistoryEntity>> getHistory();

    Observable<List<CurrencyHistoryEntity>> getHistory(long timeFrom, long timeTo);

    Observable<List<CurrencyHistoryEntity>> getHistory(int idCurrency);

    Observable<List<CurrencyHistoryEntity>> getHistory(List<Integer> currencyIds);

    Observable<List<CurrencyHistoryEntity>> getHistory(List<Integer> currencyIds, long timeFrom, long timeTo);

    void clearHistory();
}
