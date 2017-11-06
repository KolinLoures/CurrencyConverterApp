package com.example.kolin.currencyconverterapp.presentation.currency_list;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

/**
 * Created by kolin on 05.11.2017.
 */

public interface CurrencyListView {

    void showSupportCurrency(CurrencyEntity currency);

    void notifyUser(String message);
}
