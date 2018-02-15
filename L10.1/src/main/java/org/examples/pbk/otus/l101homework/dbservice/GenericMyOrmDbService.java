package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.dao.GenericDataSetMyOrmDao;
import org.examples.pbk.otus.l101homework.dataset.DataSet;

import java.util.List;

public abstract class GenericMyOrmDbService<T extends DataSet> implements DbService<T> {

    private GenericDataSetMyOrmDao<T> dao;

    public GenericMyOrmDbService(GenericDataSetMyOrmDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public void create(T entity) {
        dao.create(entity);
    }

    @Override
    public T read(Long id) {
        return dao.read(id);
    }

    @Override
    public List<T> readAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(T object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(T object) {
        throw new UnsupportedOperationException();
    }
}
