package org.examples.pbk.otus.l161homework.userService;

import org.hibernate.Session;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionUtils {
    public static <R> R runInTransaction(Function<Session, R> function) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.getTransaction().begin();
        R result = function.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static void runInTransactionWithoutResult(Consumer<Session> consumer) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.getTransaction().begin();
        consumer.accept(session);
        session.getTransaction().commit();
        session.close();
    }
}
