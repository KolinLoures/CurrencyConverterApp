package com.example.kolin.currencyconverterapp.data;

import android.util.Log;

import com.example.kolin.currencyconverterapp.domain.RatePojo;
import com.example.kolin.currencyconverterapp.domain.SupportCurrencies;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kolin on 01.11.2017.
 */

public class SupportCurrenciesDeserializer implements JsonDeserializer<SupportCurrencies> {

    private static final String TAG = SupportCurrenciesDeserializer.class.getSimpleName();

    private static final String SERIALIZED_NAME_BASE = "base";
    private static final String SERIALIZED_NAME_RATES = "rates";

    @Override
    public SupportCurrencies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SupportCurrencies obj = new SupportCurrencies();
        List<String> currencyNames = new LinkedList<>();

        if (json.isJsonObject()){

            JsonObject asJsonObject = json.getAsJsonObject();

            String base = asJsonObject.get(SERIALIZED_NAME_BASE).getAsString();
            currencyNames.add(base);

            Set<Map.Entry<String, JsonElement>> entries = asJsonObject.get(SERIALIZED_NAME_RATES).getAsJsonObject().entrySet();

            if (!entries.isEmpty()){

                for (Map.Entry<String, JsonElement> entry : entries)
                    currencyNames.add(entry.getKey());

            }

            if (currencyNames.isEmpty())
                Log.e(TAG, "deserialize: Empty names list");

            obj.setListCurrencies(currencyNames);
        }

        return obj;
    }
}