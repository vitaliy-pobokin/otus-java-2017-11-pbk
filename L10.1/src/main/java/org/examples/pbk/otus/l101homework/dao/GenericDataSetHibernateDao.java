package org.examples.pbk.otus.l101homework.dao;

import org.examples.pbk.otus.l101homework.dataset.DataSet;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDataSetHibernateDao<T extends DataSet>
        implements GenericDataSetDao<T> {

    private Class<T> persistentClass;
    private Session session;


    @SuppressWarnings("unchecked")
    public GenericDataSetHibernateDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setSession(Session s) {
        this.session = s;
    }

    protected Session getSession() {
        if (session == null)
            throw new IllegalStateException("Session has not been set on DAO before usage");
        return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public T read(Long id) {
        T entity = getSession().load(getPersistentClass(), id);
        return entity;
    }

    @Override
    public List<T> readAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getPersistentClass());
        criteria.from(getPersistentClass());
        return session.createQuery(criteria).list();
    }

    @Override
    public void create(T entity) {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }
}
