package com.example.kolin.currencyconverterapp.data.preference;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_CACHE_TIME;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_CHART_PARAM_PERIOD;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_CHART_PARAM_PICKED_CURR;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_FIRST_START;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_SEARCH_PARAM_PERIOD;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum.KEY_SEARCH_PARAM_PICKED_CURR;

/**
 * Created by kolin on 09.12.2017.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        KEY_CACHE_TIME,
        KEY_FIRST_START,
        KEY_SEARCH_PARAM_PERIOD,
        KEY_SEARCH_PARAM_PICKED_CURR,
        KEY_CHART_PARAM_PERIOD,
        KEY_CHART_PARAM_PICKED_CURR
})
public @interface PreferenceKeys {}