package org.examples.pbk.otus.l91homework.converter;

public interface TypeConverter<T> {
    String toSQL(T object);
    T fromSQL(Object sqlType);
    String getSQLTypeName();
}
