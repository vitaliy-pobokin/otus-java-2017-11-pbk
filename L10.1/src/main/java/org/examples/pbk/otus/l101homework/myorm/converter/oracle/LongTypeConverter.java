package org.examples.pbk.otus.l101homework.myorm.converter.oracle;

import org.examples.pbk.otus.l101homework.myorm.converter.TypeConverter;

import java.math.BigDecimal;

public class LongTypeConverter implements TypeConverter<Long> {
    @Override
    public String toSQL(Long object) {
        return object.toString();
    }

    @Override
    public Long fromSQL(Object sqlType) {
        return ((BigDecimal) sqlType).longValue();
    }

    @Override
    public String getSQLTypeName() {
        return "NUMBER";
    }
}
