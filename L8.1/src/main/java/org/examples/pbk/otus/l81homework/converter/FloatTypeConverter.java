package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class FloatTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Float) {
            return Json.createValue((Float) o);
        }
        throw new IllegalArgumentException();
    }
}
