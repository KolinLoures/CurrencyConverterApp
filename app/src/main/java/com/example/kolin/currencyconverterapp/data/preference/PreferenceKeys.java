package com.example.kolin.currencyconverterapp.data.preference;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_CACHE_TIME;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_CHART_PARAM_PERIOD;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_CHART_PARAM_PICKED_CURR;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_FIRST_START;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_SEARCH_PARAM_PERIOD;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceKeys.KEY_SEARCH_PARAM_PICKED_CURR;

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
public @interface PreferenceKeys {

    String KEY_CACHE_TIME = "key_pref_cache_time";
    String KEY_FIRST_START = "key_pref_first_start";

    String KEY_SEARCH_PARAM_PERIOD = "key_search_param_period";
    String KEY_SEARCH_PARAM_PICKED_CURR = "key_search_param_picked_curr";

    String KEY_CHART_PARAM_PERIOD = "key_chart_param_period";
    String KEY_CHART_PARAM_PICKED_CURR = "key_chart_param_picked_curr";
}
