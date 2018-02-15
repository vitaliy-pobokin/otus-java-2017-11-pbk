package org.examples.pbk.otus.l101homework.myorm.converter;

public interface TypeConverter<T> {
    String toSQL(T object);
    T fromSQL(Object sqlType);
    String getSQLTypeName();
}
