package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class IntegerTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Integer) {
            return Json.createValue((Integer) o);
        }
        throw new IllegalArgumentException();
    }
}
