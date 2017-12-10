package com.example.kolin.currencyconverterapp.data.dao;

import com.example.kolin.currencyconverterapp.data.common.CursorMapper;
import com.example.kolin.currencyconverterapp.data.db.DataBaseHelper;
import com.example.kolin.currencyconverterapp.data.db.tables.CurrencyCatalogTable;
import com.example.kolin.currencyconverterapp.data.db.tables.HistoryTable;
import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.10.2017.
 */

public class DataBaseQueries implements DAO {

    private static final String TAG = DataBaseQueries.class.getSimpleName();

    private DataBaseHelper db;

    public DataBaseQueries() {
        this.db = DataBaseHelper.getInstance();
    }

    @Override
    public Observable<List<CurrencyEntity>> getAllCurrency() {
        return Observable
                .fromCallable(() -> db.getCursor(CurrencyCatalogTable.selectAllCurrencies()))
                .map(CursorMapper::cursorToCurrencyEntityClass);
    }

    @Override
    public Observable<List<String>> getNames() {
        return Observable
                .fromCallable(() -> db.getCursor(CurrencyCatalogTable.selectNames()))
                .map(CursorMapper::cursorToNamesList);
    }

    @Override
    public Observable<CurrencyEntity> getAllFavoriteCurrency() {
        return Observable
                .fromCallable(() -> db.getCursor(CurrencyCatalogTable.selectAllFavoritesCurrencies()))
                .map(CursorMapper::cursorToCurrencyEntityClass)
                .flatMapIterable(currencyEntities -> currencyEntities);
    }

    @Override
    public void addCurrency(String name) {
        db.insert(CurrencyCatalogTable.TABLE_NAME, CurrencyCatalogTable.getContentValues(name));
    }

    @Override
    public void addCurrency(List<String> names) {
        long currentTime = Calendar.getInstance().getTimeInMillis();

        for (String s : names)
            db.insert(CurrencyCatalogTable.TABLE_NAME, CurrencyCatalogTable.getContentValues(s, currentTime));
    }

    @Override
    public void addCurrencyToFavorite(int id) {
        db.executeSQL(CurrencyCatalogTable.addCurrencyToFavorite(id));
    }

    @Override
    public void removeCurrencyFromFavorite(int id) {
        db.executeSQL(CurrencyCatalogTable.removeCurrencyFromFavorite(id));
    }

    @Override
    public void updateCurrencyTime(int id) {
        db.executeSQL(CurrencyCatalogTable.updateCurrencyTime(id));
    }

    @Override
    public void addHistory(int idCurrencyFrom, int idCurrencyTo, float sumFrom, float sumTo, float rate) {
        db.insert(HistoryTable.TABLE_NAME,
                HistoryTable.getContentValues(idCurrencyFrom, idCurrencyTo, sumFrom, sumTo, rate));
    }

    public Observable<List<CurrencyHistoryEntity>> getHistory() {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory()))
                .map(CursorMapper::cursorToCurrencyHistoryEntity);
    }

    private Observable<List<CurrencyHistoryEntity>> getHistory(long timeFrom, long timeTo) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(timeFrom, timeTo)))
                .map(CursorMapper::cursorToCurrencyHistoryEntity);
    }

    private Observable<List<CurrencyHistoryEntity>> getHistory(List<Integer> currencyIds) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(currencyIds)))
                .map(CursorMapper::cursorToCurrencyHistoryEntity);
    }


    private Observable<List<CurrencyHistoryEntity>> getHistory(List<Integer> currencyIds, long timeFrom, long timeTo) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(currencyIds, timeFrom, timeTo)))
                .map(CursorMapper::cursorToCurrencyHistoryEntity);
    }

    @Override
    public Observable<List<CurrencyHistoryEntity>> getHistory(SearchParam param) {
        if (param.getTimeFrom() == -1 && param.getTimeTo() == -1 && param.getCheckedCurrencies().isEmpty())
            return getHistory();
        if (param.getTimeFrom() == -1 && param.getTimeTo() == -1 && !param.getCheckedCurrencies().isEmpty())
            return getHistory(param.getCheckedCurrencies());
        if (param.getTimeFrom() != -1 && param.getTimeTo() != -1 && param.getCheckedCurrencies().isEmpty())
            return getHistory(param.getTimeFrom(), param.getTimeTo());
        if (param.getTimeFrom() != -1 && param.getTimeTo() != -1 && !param.getCheckedCurrencies().isEmpty())
            return getHistory(param.getCheckedCurrencies(), param.getTimeFrom(), param.getTimeTo());

        return null;
    }

    @Override
    public void clearHistory() {
        db.getCursor(HistoryTable.clearHistory());
    }
}
