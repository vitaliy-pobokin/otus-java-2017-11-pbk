package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class LongTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Long) {
            return Json.createValue((Long) o);
        }
        throw new IllegalArgumentException();
    }
}
