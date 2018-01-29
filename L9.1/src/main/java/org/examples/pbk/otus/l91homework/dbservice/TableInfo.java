package org.examples.pbk.otus.l91homework.dbservice;

import org.examples.pbk.otus.l91homework.converter.SQLTypeProvider;
import org.examples.pbk.otus.l91homework.dataset.DataSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {
    private String tableName;
    private List<Column> columns;

    private TableInfo() {

    }

    public static <T extends DataSet> TableInfo getTableInfo(Class<T> clazz) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.tableName = getTableName(clazz);
        tableInfo.columns = getColumnsInfo(clazz);
        return tableInfo;
    }

    private static <T extends DataSet> String getTableName(Class<T> clazz) {
        String className = clazz.getSimpleName();
        return (className.indexOf("DataSet") > 0) ?
                className.substring(0, className.indexOf("DataSet")) :
                className;
    }

    private static <T extends DataSet> List<Column> getColumnsInfo(Class<T> clazz) {
        List<Column> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            String sqlType = SQLTypeProvider.getSQLType(field.getType());
            if (sqlType != null)
                columns.add(new Column(field.getName(), field.getType(), sqlType));
        }
        return columns;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
