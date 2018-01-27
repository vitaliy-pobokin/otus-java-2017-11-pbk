package org.examples.pbk.otus.l81homework.converter;

import javax.json.Json;
import javax.json.JsonValue;

public interface TypeConverter<T extends JsonValue> {
    T convert(Object o);
}
