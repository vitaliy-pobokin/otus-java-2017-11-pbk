package org.examples.pbk.otus.l91homework.dbservice;

import org.examples.pbk.otus.l91homework.converter.SQLTypeProvider;
import org.examples.pbk.otus.l91homework.executor.BaseExecutor;
import org.examples.pbk.otus.l91homework.dataset.DataSet;
import org.examples.pbk.otus.l91homework.connection.ConnectionHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl() {
        this.connection = ConnectionHelper.getConnection();
    }

    @Override
    public <T extends DataSet> void createTable(Class<T> clazz) throws SQLException {
        String sql = prepareCreateTableSQL(clazz);
        BaseExecutor executor = new BaseExecutor(connection);
        executor.execUpdate(sql);
    }

    @Override
    public <T extends DataSet> void deleteTable(Class<T> clazz) throws SQLException {
        String sql = prepareDropTableSQL(clazz);
        BaseExecutor executor = new BaseExecutor(connection);
        executor.execUpdate(sql);
    }

    @Override
    public <T extends DataSet> void save(T entity) throws SQLException {
        String sql = prepareInsertSQL(entity);
        BaseExecutor executor = new BaseExecutor(connection);
        executor.execUpdate(sql);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        TableInfo table = TableInfo.getTableInfo(clazz);
        String sql = String.format("SELECT * FROM %s WHERE id=%d", table.getTableName(), id);
        BaseExecutor executor = new BaseExecutor(connection);
        T dataSet = executor.execQuery(sql, rs -> {
            rs.next();
            T result = null;
            try {
                result = clazz.newInstance();
                for (Column column : table.getColumns()) {
                    Object columnValue = rs.getObject(column.getName());
                    Field fieldToSet = clazz.getDeclaredField(column.getName());
                    boolean isAccessible = fieldToSet.isAccessible();
                    fieldToSet.setAccessible(true);
                    fieldToSet.set(result, SQLTypeProvider.getTypeConverter(column.getJavaType()).fromSQL(columnValue));
                    fieldToSet.setAccessible(isAccessible);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return result;
        });
        return dataSet;
    }

    private <T extends DataSet> String prepareCreateTableSQL(Class<T> clazz) {
        TableInfo table = TableInfo.getTableInfo(clazz);
        StringBuilder columns = new StringBuilder();
        for (Column column : table.getColumns()) {
            columns.append(column.getName()).append(" ").append(column.getDbType()).append(",");
        }
        columns.deleteCharAt(columns.lastIndexOf(","));
        return String.format("CREATE TABLE %s (%s)", table.getTableName(), columns.toString());
    }

    private <T extends DataSet> String prepareDropTableSQL(Class<T> clazz) {
        TableInfo table = TableInfo.getTableInfo(clazz);
        return String.format("DROP TABLE %s", table.getTableName());
    }

    private <T extends DataSet> String prepareInsertSQL(T entity) {
        TableInfo table = TableInfo.getTableInfo(entity.getClass());
        return String.format("INSERT INTO %s VALUES(%s)", table.getTableName(), getValuesToInsert(table, entity));
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
            value = field.get(entity);
            field.setAccessible(isAccessible);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
