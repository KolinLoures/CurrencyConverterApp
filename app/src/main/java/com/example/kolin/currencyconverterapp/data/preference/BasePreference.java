package com.example.kolin.currencyconverterapp.data.preference;

/**
 * Interface for work with preference
 */

public interface BasePreference {

    /**
     * Get preference
     *
     * @param key of preference. Can only set {@link PreferenceKeysEnum} values
     * @param type of preference. Can only set {@link PreferenceTypeEnum} values
     * @param defValue default value
     * @return Preference
     */
    Object getFromPreference(@PreferenceKeys String key, @PreferenceType int type, Object defValue);

    /**
     * Put object to preference
     *
     * @param key of preference. Can only set {@link PreferenceKeysEnum} values
     * @param type of preference. Can only set {@link PreferenceTypeEnum} values
     * @param value to put
     */
    void putToPreference(@PreferenceKeys String key, @PreferenceType int type, Object value);

}
