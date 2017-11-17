package com.example.kolin.currencyconverterapp.data.db;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.10.2017.
 */

public interface DAO {

    interface CurrencyCatalogDAO {
        Observable<CurrencyEntity> getAllCurrency();

        Observable<CurrencyEntity> getAllFavoriteCurrency();

        void addCurrency(String name);

        void addCurrency(List<String> names);

        void addCurrencyToFavorite(int id);

        void removeCurrencyFromFavorite(int id);

        void updateCurrencyTime(int id);
    }

    interface HistoryCurrencyDAO {
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
}
