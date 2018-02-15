package org.examples.pbk.otus.l101homework.myorm;

import org.examples.pbk.otus.l101homework.myorm.converter.SQLTypeProvider;
import org.examples.pbk.otus.l101homework.dataset.DataSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableInfo {
    private String tableName;
    private List<Column> columns;
    private SQLStatements sqlStatements;

    private TableInfo(Class<? extends DataSet> clazz) {
        this.tableName = getTableName(clazz);
        this.columns = getColumnsInfo(clazz);
        this.sqlStatements = prepareSQLStatementsForClass(clazz);
    }

    public static TableInfo getTableInfo(Class<? extends DataSet> clazz) {
        return new TableInfo(clazz);
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public SQLStatements getSqlStatements() {
        return sqlStatements;
    }

    private String getTableName(Class<? extends DataSet> clazz) {
        String className = clazz.getSimpleName();
        return (className.indexOf("DataSet") > 0) ?
                className.substring(0, className.indexOf("DataSet")) :
                className;
    }

    private List<Column> getColumnsInfo(Class<? extends DataSet> clazz) {
        List<Column> columns = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            Class fieldType = field.getType();
            String fieldName = field.getName();
            String sqlType = SQLTypeProvider.getSQLType(fieldType);
            if (DataSet.class.isAssignableFrom(fieldType)) {
                sqlType = SQLTypeProvider.getSQLType(Long.class);
            }
            if (sqlType != null)
                columns.add(new Column(fieldName, fieldType, sqlType));
        }
        return columns;
    }

    SQLStatements prepareSQLStatementsForClass(Class<? extends DataSet> clazz) {
        return new SQLStatements(clazz);
    }

    class SQLStatements {
        private String createTableSQL;
        private String dropTableSQL;
        private String selectSQL;
        private String insertSQL;

        private SQLStatements(Class<? extends DataSet> clazz) {
            this.createTableSQL = prepareCreateTableSQL(clazz);
            this.dropTableSQL = prepareDropTableSQL(clazz);
            this.selectSQL = prepareSelectSQL(clazz);
            this.insertSQL = prepareInsertSQL(clazz);
        }

        String getCreateTableSQL() {
            return createTableSQL;
        }

        String getDropTableSQL() {
            return dropTableSQL;
        }

        String getSelectSQL() {
            return selectSQL;
        }

        String getInsertSQL() {
            return insertSQL;
        }

        private String prepareCreateTableSQL(Class<? extends DataSet> clazz) {
            StringBuilder sb = new StringBuilder();
            for (Column column : columns) {
                sb.append(column.getName()).append(" ").append(column.getDbType()).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            return String.format("CREATE TABLE %s (%s)", tableName, sb.toString());
        }

        private String prepareDropTableSQL(Class<? extends DataSet> clazz) {
            return String.format("DROP TABLE %s", tableName);
        }

        private String prepareSelectSQL(Class<? extends DataSet> clazz) {
            return String.format("SELECT * FROM %s WHERE id=?", tableName);
        }

        private String prepareInsertSQL(Class<? extends DataSet> clazz) {
            return String.format("INSERT INTO %s VALUES(?)", tableName);
        }
    }
}
