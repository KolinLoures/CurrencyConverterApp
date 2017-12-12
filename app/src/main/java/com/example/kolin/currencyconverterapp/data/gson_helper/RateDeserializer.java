package com.example.kolin.currencyconverterapp.data.gson_helper;

import com.example.kolin.currencyconverterapp.data.model.RatePojo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Json deserializer for {@link RatePojo}
 */
public class RateDeserializer implements JsonDeserializer<RatePojo> {

    @Override
    public RatePojo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RatePojo obj = new RatePojo();

        if (json.isJsonObject()) {

            JsonObject asJsonObject = json.getAsJsonObject();

            String base = asJsonObject.get(JsonElementKey.SERIALIZED_NAME_BASE).getAsString();
            obj.setCurrencyFrom(base);

            String date = asJsonObject.get(JsonElementKey.SERIALIZED_NAME_DATE).getAsString();
            obj.setDate(date);

            Set<Map.Entry<String, JsonElement>> entries =
                    asJsonObject.get(JsonElementKey.SERIALIZED_NAME_RATES).getAsJsonObject().entrySet();

            if (!entries.isEmpty()) {
                Map.Entry<String, JsonElement> next = entries.iterator().next();

                obj.setCurrencyTo(next.getKey());
                obj.setRate(next.getValue().getAsFloat());

            }
        }

        return obj;
    }
}
