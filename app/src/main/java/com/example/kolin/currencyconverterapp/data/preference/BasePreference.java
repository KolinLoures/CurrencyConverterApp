package com.example.kolin.currencyconverterapp.data.preference;

/**
 * Created by kolin on 09.12.2017.
 */

public interface BasePreference {

    Object getFromPreference(@PreferenceKeys String key, @PreferenceType int type, Object defValue);

    void putToPreference(@PreferenceKeys String key, @PreferenceType int type, Object value);

}
