package org.examples.pbk.otus.l91homework.dbservice;

import org.examples.pbk.otus.l91homework.dataset.DataSet;

import java.sql.SQLException;

public interface DBService {
    <T extends DataSet> void createTable(Class<T> clazz) throws SQLException;
    <T extends DataSet> void deleteTable(Class<T> clazz) throws SQLException;
    <T extends DataSet> void save(T entity) throws SQLException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
