package org.examples.pbk.otus.l91homework.converter.oracle;

import org.examples.pbk.otus.l91homework.converter.TypeConverter;

import java.math.BigDecimal;

public class IntegerTypeConverter implements TypeConverter<Integer> {
    @Override
    public String toSQL(Integer object) {
        return object.toString();
    }

    @Override
    public Integer fromSQL(Object sqlType) {
        return ((BigDecimal) sqlType).intValue();
    }

    @Override
    public String getSQLTypeName() {
        return "NUMBER";
    }
}
