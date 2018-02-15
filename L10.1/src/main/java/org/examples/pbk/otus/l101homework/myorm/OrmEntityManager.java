package org.examples.pbk.otus.l101homework.myorm;

import org.examples.pbk.otus.l101homework.dataset.DataSet;
import org.examples.pbk.otus.l101homework.myorm.converter.SQLTypeProvider;
import org.examples.pbk.otus.l101homework.myorm.executor.BaseExecutor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class OrmEntityManager {

    private Connection connection;
    private Map<Class<? extends DataSet>, TableInfo> dbTables;

    OrmEntityManager(Connection connection, Map<Class<? extends DataSet>, TableInfo> dbTables) {
        this.connection = connection;
        this.dbTables = dbTables;
    }

    public <T extends DataSet> void save(T entity) {
        String sql = prepareInsertSQL(entity);
        BaseExecutor executor = new BaseExecutor(connection);
        try {
            executor.execUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        TableInfo table = dbTables.get(clazz);
        String sql = table.getSqlStatements().getSelectSQL().replace("?", String.valueOf(id));
        BaseExecutor executor = new BaseExecutor(connection);
        T dataSet = null;
        try {
            dataSet = executor.execQuery(sql, rs -> {
                rs.next();
                T result = null;
                try {
                    result = clazz.newInstance();
                    for (Column column : table.getColumns()) {
                        Object columnValue = rs.getObject(column.getName());
                        Field fieldToSet = clazz.getDeclaredField(column.getName());
                        boolean isAccessible = fieldToSet.isAccessible();
                        fieldToSet.setAccessible(true);
                        if (DataSet.class.isAssignableFrom(column.getJavaType())) {
                            fieldToSet.set(result, load(((BigDecimal)columnValue).longValue(), (Class<T>)column.getJavaType()));
                        } else {
                            fieldToSet.set(result, SQLTypeProvider.getTypeConverter(column.getJavaType()).fromSQL(columnValue));
                        }
                        fieldToSet.setAccessible(isAccessible);
                    }
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                return result;
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSet;
    }

    private <T extends DataSet> String prepareInsertSQL(T entity) {
        TableInfo table = dbTables.get(entity.getClass());
        return table.getSqlStatements().getInsertSQL().replace("?", getValuesToInsert(table, entity));
    }

    private <T extends DataSet> String getValuesToInsert(TableInfo table, T entity) {
        StringBuilder values = new StringBuilder();
        for (Column column : table.getColumns()) {
            Object value = getColumnValue(column, entity);
            if (column.getJavaType() == String.class) {
                values.append('\'').append(value.toString()).append('\'');
            } else {
                values.append(value.toString());
            }
            values.append(",");
        }
        values.deleteCharAt(values.lastIndexOf(","));
        return values.toString();
    }

    private <T extends DataSet> Object getColumnValue(Column column, T entity) {
        Object value = null;
        try {
            Field field = entity.getClass().getDeclaredField(column.getName());
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if (DataSet.class.isAssignableFrom(field.getType())) {
                save((DataSet)field.get(entity));
                value = ((DataSet)field.get(entity)).getId();
            } else {
                value = field.get(entity);
            }
            field.setAccessible(isAccessible);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
