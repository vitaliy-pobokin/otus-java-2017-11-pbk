package org.examples.pbk.otus.l101homework.dbservice;

import org.examples.pbk.otus.l101homework.PersistenceManager;
import org.examples.pbk.otus.l101homework.dao.GenericDataSetHibernateDao;
import org.examples.pbk.otus.l101homework.dataset.DataSet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GenericHibernateDbService<T extends DataSet> implements DbService<T> {

    private GenericDataSetHibernateDao<T> dao;

    public GenericHibernateDbService(GenericDataSetHibernateDao<T> dao) {
        this.dao = dao;
    }

    public void create(T object) {
        runInSessionWithVoidResult(session -> {
            dao.setSession(session);
            dao.create(object);
        });
    }

    public T read(Long id) {
        return runInSession(session -> {
            dao.setSession(session);
            return dao.read(id);
        });
    }

    public List<T> readAll() {
        return runInSession(session -> {
            dao.setSession(session);
            return dao.readAll();
        });
    }

    public void update(T object) {
        runInSessionWithVoidResult(session -> {
            dao.setSession(session);
            dao.update(object);
        });
    }

    public void delete(T object) {
        runInSessionWithVoidResult(session -> {
            dao.setSession(session);
            dao.delete(object);
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
