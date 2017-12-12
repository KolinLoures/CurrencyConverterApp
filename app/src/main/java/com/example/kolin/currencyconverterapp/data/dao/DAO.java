package com.example.kolin.currencyconverterapp.data.dao;

import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface class for queries to data base.
 */

public interface DAO {

    /**
     * Get all {@link CurrencyEntity} from data base
     *
     * @return {@link List<CurrencyEntity>} object
     */
    Observable<List<CurrencyEntity>> getAllCurrency();

    /**
     * Get all favorite {@link CurrencyEntity} from data base
     *
     * @return {@link List<CurrencyEntity>} object with favorites
     */
    Observable<CurrencyEntity> getAllFavoriteCurrency();

    /**
     * Get names of currencies that in data base
     *
     * @return {@link List<String>} object with currencies names
     */
    Observable<List<String>> getNames();

    /**
     * Put currency to data base
     *
     * @param name of currency
     */
    void addCurrency(String name);

    /**
     * Put {@link List} of currencies to data base
     *
     * @param names {@link List} of currencies names
     */
    void addCurrency(List<String> names);

    /**
     * Add currency to favorite
     *
     * @param id of currency
     */
    void addCurrencyToFavorite(int id);

    /**
     * Remove currency from favorite
     *
     * @param id of currency
     */
    void removeCurrencyFromFavorite(int id);

    /**
     * Update last use time for currency
     *
     * @param id of currency
     */
    void updateCurrencyTime(int id);

    /**
     * Add change to history
     *
     * @param idCurrencyFrom currency from
     * @param idCurrencyTo currency to
     * @param sumFrom sum from
     * @param sumTo sum to
     * @param rate currenct rate
     */
    void addHistory(int idCurrencyFrom,
                    int idCurrencyTo,
                    float sumFrom,
                    float sumTo,
                    float rate);

    /**
     * Get History in according with {@link SearchParam}
     *
     * @param param {@link SearchParam} object
     * @return {@link Observable<List<CurrencyHistoryEntity>>} object
     */
    Observable<List<CurrencyHistoryEntity>> getHistory(SearchParam param);

    /**
     * Clear data base history
     */
    void clearHistory();
}
