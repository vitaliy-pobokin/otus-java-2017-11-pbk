package org.examples.pbk.otus.l111homework.dbservice;

import org.examples.pbk.otus.l111homework.PersistenceManager;
import org.examples.pbk.otus.l111homework.cache.CacheEngine;
import org.examples.pbk.otus.l111homework.cache.Element;
import org.examples.pbk.otus.l111homework.dao.GenericDataSetHibernateDao;
import org.examples.pbk.otus.l111homework.dataset.DataSet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GenericCachedHibernateDbService<T extends DataSet> implements DbService<T> {
    private GenericDataSetHibernateDao<T> dao;
    private CacheEngine<Long, T> cacheEngine;

    public GenericCachedHibernateDbService(GenericDataSetHibernateDao<T> dao, CacheEngine<Long, T> cacheEngine) {
        this.dao = dao;
        this.cacheEngine = cacheEngine;
    }

    public void create(T object) {
        runInSessionWithVoidResult(session -> {
            dao.setSession(session);
            dao.create(object);
        });
        cacheEngine.put(new Element<>(object.getId(), object));
    }

    public T read(Long id) {
        Element<Long, T> element = cacheEngine.get(id);
        if (element != null) {
            return element.getValue();
        }
        return runInSession(session -> {
            dao.setSession(session);
            return dao.read(id);
        });
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = PersistenceManager.getInstance().openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void runInSessionWithVoidResult(Consumer<Session> consumer) {
        try (Session session = PersistenceManager.getInstance().openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }
}
