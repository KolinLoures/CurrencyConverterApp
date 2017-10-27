package com.example.kolin.currencyconverterapp.data.db;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import io.reactivex.Flowable;

/**
 * Created by kolin on 27.10.2017.
 */

public interface DAO {

    interface CurrencyCatalogDAO {
        Flowable<CurrencyEntity> getAllCurrency();

        Flowable<CurrencyEntity> getAllFavoriteCurrency();

        void addCurrency(String name);

        void addCurrencyToFavorite(int id);

        void removeCurrencyFromFavorite(int id);

        void updateCurrencyTime(int id);
    }

    interface HistoryCurrencyDAO {
        void addHistory(int idCurrencyFrom,
                        int idCurrencyTo,
                        int sumFrom,
                        int sumTo,
                        float rate);

        Flowable<CurrencyHistoryEntity> getHistory();

        Flowable<CurrencyHistoryEntity> getHistory(long timeFrom, long timeTo);

        Flowable<CurrencyHistoryEntity> getHistory(String currencyName);

        Flowable<CurrencyHistoryEntity> getHistory(String[] currencyNames);

        Flowable<CurrencyHistoryEntity> getHistory(String[] currencyNames, long timeFrom, long timeTo);

        void clearHistory();
    }
}
