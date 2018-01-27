package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class DoubleTypeConverter implements TypeConverter<JsonValue> {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Double) {
            return Json.createValue((Double) o);
        }
        throw new IllegalArgumentException();
    }
}
