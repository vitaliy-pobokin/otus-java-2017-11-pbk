package org.examples.pbk.otus.l101homework.myorm.converter;

import org.examples.pbk.otus.l101homework.myorm.converter.oracle.IntegerTypeConverter;
import org.examples.pbk.otus.l101homework.myorm.converter.oracle.LongTypeConverter;
import org.examples.pbk.otus.l101homework.myorm.converter.oracle.StringTypeConverter;

import java.util.HashMap;
import java.util.Map;

public class SQLTypeProvider {
    private static final Map<Class<?>, TypeConverter> CLASS_TO_SQL_TYPE_MAP = new HashMap<>();
    static {
        CLASS_TO_SQL_TYPE_MAP.put(String.class, new StringTypeConverter());
        CLASS_TO_SQL_TYPE_MAP.put(int.class, new IntegerTypeConverter());
        CLASS_TO_SQL_TYPE_MAP.put(Integer.class, new IntegerTypeConverter());
        CLASS_TO_SQL_TYPE_MAP.put(long.class, new LongTypeConverter());
        CLASS_TO_SQL_TYPE_MAP.put(Long.class, new LongTypeConverter());
    }

    public static String getSQLType(Class<?> type) {
        TypeConverter converter = CLASS_TO_SQL_TYPE_MAP.get(type);
        if (converter == null) {
            return null;
        }
        return CLASS_TO_SQL_TYPE_MAP.get(type).getSQLTypeName();
    }

    public static TypeConverter getTypeConverter(Class<?> type) {
        return CLASS_TO_SQL_TYPE_MAP.get(type);
    }
}
