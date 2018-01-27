package org.examples.pbk.otus.l81homework.converter;

import javax.json.JsonValue;

public class BooleanTypeConverter implements TypeConverter {
    @Override
    public JsonValue convert(Object o) {
        if (o instanceof Boolean) {
            Boolean b = (Boolean) o;
            if (b)
                return JsonValue.TRUE;
            else
                return JsonValue.FALSE;
        }
        throw new IllegalArgumentException();
    }
}
