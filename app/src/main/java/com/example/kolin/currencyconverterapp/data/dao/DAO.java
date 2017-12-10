package com.example.kolin.currencyconverterapp.data.dao;

import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.10.2017.
 */

public interface DAO {

    Observable<List<CurrencyEntity>> getAllCurrency();

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

    Observable<List<CurrencyHistoryEntity>> getHistory(SearchParam param);

    void clearHistory();
}
