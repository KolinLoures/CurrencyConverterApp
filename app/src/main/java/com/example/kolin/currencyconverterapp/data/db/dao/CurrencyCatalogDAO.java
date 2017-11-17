package com.example.kolin.currencyconverterapp.data.db.dao;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 17.11.2017.
 */

public interface CurrencyCatalogDAO extends DAO {

    Observable<CurrencyEntity> getAllCurrency();

    Observable<CurrencyEntity> getAllFavoriteCurrency();

    void addCurrency(String name);

    void addCurrency(List<String> names);

    void addCurrencyToFavorite(int id);

    void removeCurrencyFromFavorite(int id);

    void updateCurrencyTime(int id);

}
