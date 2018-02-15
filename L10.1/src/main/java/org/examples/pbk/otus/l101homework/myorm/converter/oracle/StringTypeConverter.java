package org.examples.pbk.otus.l101homework.myorm.converter.oracle;

import org.examples.pbk.otus.l101homework.myorm.converter.TypeConverter;

public class StringTypeConverter implements TypeConverter<String> {
    @Override
    public String toSQL(String object) {
        return object;
    }

    @Override
    public String fromSQL(Object sqlType) {
        return sqlType.toString();
    }

    @Override
    public String getSQLTypeName() {
        return "VARCHAR2(255)";
    }
}
