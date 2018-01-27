package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class StringTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof String) {
            return Json.createValue((String) o);
        }
        throw new IllegalArgumentException();
    }
}
