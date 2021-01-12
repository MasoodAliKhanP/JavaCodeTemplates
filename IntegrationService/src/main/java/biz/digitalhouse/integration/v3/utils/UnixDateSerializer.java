package biz.digitalhouse.integration.v3.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author kirill.arbuzov
 * @created 26/03/2018
 */
public class UnixDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Date(json.getAsJsonPrimitive().getAsLong());
    }
}
