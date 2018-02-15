package org.examples.pbk.otus.l101homework.myorm.converter.oracle;

import org.examples.pbk.otus.l101homework.myorm.converter.TypeConverter;

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
