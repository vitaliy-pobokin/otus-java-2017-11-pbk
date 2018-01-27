package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class ByteTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Byte) {
            return Json.createValue((Byte) o);
        }
        throw new IllegalArgumentException();
    }
}
