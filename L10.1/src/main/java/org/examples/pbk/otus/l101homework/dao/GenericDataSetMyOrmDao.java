package org.examples.pbk.otus.l101homework.dao;

import org.examples.pbk.otus.l101homework.dataset.DataSet;
import org.examples.pbk.otus.l101homework.myorm.OrmEntityManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDataSetMyOrmDao<T extends DataSet> implements GenericDataSetDao<T> {

    private Class<T> persistentClass;
    private OrmEntityManager em;

    @SuppressWarnings("unchecked")
    public GenericDataSetMyOrmDao(OrmEntityManager entityManager) {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.em = entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public void create(T entity) {
        em.save(entity);
    }

    @Override
    public T read(Long id) {
        return em.load(id, getPersistentClass());
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
