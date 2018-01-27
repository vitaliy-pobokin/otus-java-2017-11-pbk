package org.examples.pbk.otus.l81homework;

import org.examples.pbk.otus.l81homework.converter.*;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class JsonFramework {

    private final Map<Class, TypeConverter> converters = new HashMap<>();

    public JsonFramework() {
        initDefaultConverters();
    }

    private void initDefaultConverters() {
        converters.put(Byte.class, new ByteTypeConverter());
        converters.put(Short.class, new ShortTypeConverter());
        converters.put(Integer.class, new IntegerTypeConverter());
        converters.put(Long.class, new LongTypeConverter());
        converters.put(Float.class, new FloatTypeConverter());
        converters.put(Double.class, new DoubleTypeConverter());
        converters.put(Boolean.class, new BooleanTypeConverter());
        converters.put(Character.class, new CharacterTypeConverter());
        converters.put(String.class, new StringTypeConverter());
    }

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        } else if (isConvertible(obj.getClass())) {
            return converters.get(obj.getClass()).convert(obj).toString();
        } else if (obj.getClass().isArray()) {
            return getJsonArray(obj).toString();
        } else if (obj instanceof Collection) {
            return getJsonArray(((Collection) obj).toArray()).toString();
        } else {
            JsonObject jsonObject = getJsonObject(obj);
            return jsonObject.toString();
        }
    }

    public void addCustomConverter(Class aClass, TypeConverter converter) {
        converters.put(aClass, converter);
    }

    private JsonObject getJsonObject(Object obj) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Field fields[] = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean initialAccess = field.isAccessible();
            try {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    builder.add(field.getName(), JsonValue.NULL);
                } else if (isConvertible(field.get(obj).getClass())) {
                    builder.add(field.getName(), converters.get(field.get(obj).getClass()).convert(field.get(obj)));
                } else if (field.getType().isArray()) {
                    builder.add(field.getName(), getJsonArray(field.get(obj)));
                } else if (field.get(obj) instanceof Collection) {
                    builder.add(field.getName(), getJsonArray(((Collection) field.get(obj)).toArray()));
                } else if (hasConvertibleFields(field.get(obj))) {
                    builder.add(field.getName(), getJsonObject(field.get(obj)));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(initialAccess);
            }
        }
        return builder.build();
    }

    private JsonArray getJsonArray(Object array) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object element = Array.get(array, i);
            if (element == null) {
                arrayBuilder.add(i, JsonValue.NULL);
            } else if (isConvertible(element.getClass())) {
                arrayBuilder.add(i, converters.get(element.getClass()).convert(element));
            } else if (element.getClass().isArray()) {
                arrayBuilder.add(i, getJsonArray(element));
            } else if (element instanceof Collection) {
                arrayBuilder.add(i, getJsonArray(((Collection) element).toArray()));
            } else if (hasConvertibleFields(element)) {
                arrayBuilder.add(i, getJsonObject(element));
            }
        }
        return arrayBuilder.build();
    }

    private boolean hasConvertibleFields(Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            boolean isAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                if (field.getType().isPrimitive() || isConvertible(field.get(o).getClass())) return true;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(isAccessible);
            }
        }
        return false;
    }

    private boolean isConvertible(Class c) {
        return c.isPrimitive() || converters.keySet().contains(c);
    }
}
