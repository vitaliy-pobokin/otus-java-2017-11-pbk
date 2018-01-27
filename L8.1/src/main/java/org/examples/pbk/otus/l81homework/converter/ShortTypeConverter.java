package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class ShortTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Short) {
            return Json.createValue((Short) o);
        }
        throw new IllegalArgumentException();
    }
}
