package com.example.kolin.currencyconverterapp.data.db;

import android.content.Context;
import android.database.Cursor;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyHistoryEntity;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 27.10.2017.
 */

public class DataBaseQueries implements DAO.HistoryCurrencyDAO, DAO.CurrencyCatalogDAO {

    private static final String TAG = DataBaseQueries.class.getSimpleName();

    private static DataBaseQueries instance = null;

    private DataBaseHelper db;

    private DataBaseQueries(Context context) {
        this.db = new DataBaseHelper(context);
    }

    public static void initializeInstanceWithContext(Context context){
        if (instance == null)
            instance = new DataBaseQueries(context);
    }

    public static DataBaseQueries getInstance(){
        return instance;
    }

    @Override
    public Observable<CurrencyEntity> getAllCurrency() {
        return Observable
                .fromCallable(() -> db.getCursor(CurrencyCatalogTable.selectAllCurrencies()))
                .map(this::cursorToCurrencyEntityClass)
                .flatMapIterable(currencyEntities -> currencyEntities);
    }

    @Override
    public Observable<CurrencyEntity> getAllFavoriteCurrency() {
        return Observable
                .fromCallable(() -> db.getCursor(CurrencyCatalogTable.selectAllFavoritesCurrencies()))
                .map(this::cursorToCurrencyEntityClass)
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
    public void addHistory(String currencyFrom, String currencyTo, int sumFrom, int sumTo, float rate) {
        db.insert(HistoryTable.TABLE_NAME,
                HistoryTable.getContentValues(currencyFrom, currencyTo, sumFrom, sumTo, rate));
    }

    @Override
    public Observable<CurrencyHistoryEntity> getHistory() {

        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory()))
                .map(this::cursorToCurrencyHistoryEntity)
                .flatMapIterable(currencyHistoryEntities -> currencyHistoryEntities);
    }

    @Override
    public Observable<CurrencyHistoryEntity> getHistory(long timeFrom, long timeTo) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(timeFrom, timeTo)))
                .map(this::cursorToCurrencyHistoryEntity)
                .flatMapIterable(currencyHistoryEntities -> currencyHistoryEntities);
    }

    @Override
    public Observable<CurrencyHistoryEntity> getHistory(String currencyName) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(currencyName)))
                .map(this::cursorToCurrencyHistoryEntity)
                .flatMapIterable(currencyHistoryEntities -> currencyHistoryEntities);
    }

    @Override
    public Observable<CurrencyHistoryEntity> getHistory(String[] currencyNames) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(currencyNames)))
                .map(this::cursorToCurrencyHistoryEntity)
                .flatMapIterable(currencyHistoryEntities -> currencyHistoryEntities);
    }

    @Override
    public Observable<CurrencyHistoryEntity> getHistory(String[] currencyNames, long timeFrom, long timeTo) {
        return Observable
                .fromCallable(() -> db.getCursor(HistoryTable.selectHistory(currencyNames,  timeFrom, timeTo)))
                .map(this::cursorToCurrencyHistoryEntity)
                .flatMapIterable(currencyHistoryEntities -> currencyHistoryEntities);
    }

    @Override
    public void clearHistory() {
        db.getCursor(HistoryTable.clearHistory());
    }

    private List<CurrencyEntity> cursorToCurrencyEntityClass(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst())
            try {

                List<CurrencyEntity> temp = new LinkedList<>();

                do {
                    int id = cursor.getInt(cursor.getColumnIndex(CurrencyCatalogTable.ID));
                    String name = cursor.getString(cursor.getColumnIndex(CurrencyCatalogTable.NAME));
                    boolean isFavorite = cursor.getInt(cursor.getColumnIndex(CurrencyCatalogTable.IS_FAVORITE)) != 0;
                    long lastUse = cursor.getInt(cursor.getColumnIndex(CurrencyCatalogTable.TIME));

                    CurrencyEntity obj = new CurrencyEntity();

                    obj.setId(id);
                    obj.setName(name);
                    obj.setFavorite(isFavorite);
                    obj.setLastUse(lastUse);

                    temp.add(obj);

                } while (cursor.moveToNext());

                cursor.close();

                return temp;

            } finally {
                cursor.close();
            }

        return null;
    }

    private List<CurrencyHistoryEntity> cursorToCurrencyHistoryEntity(Cursor cursor) {
        if (cursor != null && cursor.moveToNext())
            try {

                List<CurrencyHistoryEntity> temp = new LinkedList<>();

                do {

                    int id = cursor.getInt(cursor.getColumnIndex(HistoryTable.ID));
                    String from = cursor.getString(cursor.getColumnIndex(HistoryTable.CURRENCY_FROM));
                    String to = cursor.getString(cursor.getColumnIndex(HistoryTable.CURRENCY_TO));
                    int sumFrom = cursor.getInt(cursor.getColumnIndex(HistoryTable.SUM_FROM));
                    int sumTo = cursor.getInt(cursor.getColumnIndex(HistoryTable.SUM_TO));
                    float rate = cursor.getInt(cursor.getColumnIndex(HistoryTable.RATE));
                    long time = cursor.getInt(cursor.getColumnIndex(HistoryTable.TIME));

                    CurrencyHistoryEntity obj = new CurrencyHistoryEntity();

                    obj.setId(id);
                    obj.setCurrencyFrom(from);
                    obj.setCurrencyTo(to);
                    obj.setSumFrom(sumFrom);
                    obj.setSumTo(sumTo);
                    obj.setRate(rate);
                    obj.setTime(time);

                    temp.add(obj);

                } while (cursor.moveToNext());


                return temp;

            } finally {
                cursor.close();
            }
        return null;
    }
}
