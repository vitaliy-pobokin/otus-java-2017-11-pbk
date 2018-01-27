package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public class CharacterTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Character) {
            return Json.createValue(o.toString());
        }
        throw new IllegalArgumentException();
    }
}
