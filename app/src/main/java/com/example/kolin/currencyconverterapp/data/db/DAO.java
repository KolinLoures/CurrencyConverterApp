package com.example.kolin.currencyconverterapp.data.db;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import java.util.List;

/**
 * Created by kolin on 27.10.2017.
 */

public interface DAO {

    interface CurrencyCatalogDAO {
        List<CurrencyEntity> getAllCurrency();

        List<CurrencyEntity> getAllFavoriteCurrency();

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

        List<CurrencyHistoryEntity> getHistory();

        List<CurrencyHistoryEntity> getHistory(long timeFrom, long timeTo);

        List<CurrencyHistoryEntity> getHistory(String currencyName);

        List<CurrencyHistoryEntity> getHistory(String[] currencyNames);

        List<CurrencyHistoryEntity> getHistory(String[] currencyNames, long timeFrom, long timeTo);

        void clearHistory();
    }
}
