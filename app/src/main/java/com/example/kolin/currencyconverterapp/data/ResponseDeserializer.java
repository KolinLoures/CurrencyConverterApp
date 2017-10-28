package com.example.kolin.currencyconverterapp.data;

import com.example.kolin.currencyconverterapp.domain.RatePojo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kolin on 28.10.2017.
 */

public class ResponseDeserializer implements JsonDeserializer<RatePojo> {

    @Override
    public RatePojo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RatePojo obj = new RatePojo();

        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();

            if (entries.size() > 1){
                HashMap<String, Float> rates = new HashMap<>();

                for (Map.Entry<String, JsonElement> next : entries) {
                    rates.put(next.getKey(), next.getValue().getAsFloat());
                }

                obj.setRates(rates);
            }
        }

        return obj;
    }
}
