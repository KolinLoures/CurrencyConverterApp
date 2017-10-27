package com.example.kolin.currencyconverterapp.data.db;

import android.content.Context;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import java.util.List;

/**
 * Created by kolin on 27.10.2017.
 */

public class DataBaseQueries implements DAO.HistoryCurrencyDAO, DAO.CurrencyCatalogDAO {

    private static final String TAG = DataBaseQueries.class.getSimpleName();

    private DataBaseHelper db;

    public DataBaseQueries(Context context) {
        this.db = new DataBaseHelper(context);
    }


    @Override
    public List<CurrencyEntity> getAllCurrency() {
        return null;
    }

    @Override
    public List<CurrencyEntity> getAllFavoriteCurrency() {
        return null;
    }

    @Override
    public void addCurrency(String name) {

    }

    @Override
    public void addCurrencyToFavorite(int id) {

    }

    @Override
    public void removeCurrencyFromFavorite(int id) {

    }

    @Override
    public void updateCurrencyTime(int id) {

    }

    @Override
    public void addHistory(int idCurrencyFrom, int idCurrencyTo, int sumFrom, int sumTo, float rate) {

    }

    @Override
    public List<CurrencyHistoryEntity> getHistory() {
        return null;
    }

    @Override
    public List<CurrencyHistoryEntity> getHistory(long timeFrom, long timeTo) {
        return null;
    }

    @Override
    public List<CurrencyHistoryEntity> getHistory(String currencyName) {
        return null;
    }

    @Override
    public List<CurrencyHistoryEntity> getHistory(String[] currencyNames) {
        return null;
    }

    @Override
    public List<CurrencyHistoryEntity> getHistory(String[] currencyNames, long timeFrom, long timeTo) {
        return null;
    }

    @Override
    public void clearHistory() {

    }
}
