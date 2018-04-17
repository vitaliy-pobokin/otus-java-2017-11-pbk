package org.examples.pbk.otus.l151homework.dbService;

import org.examples.pbk.otus.l151homework.entity.User;

import java.util.List;

public class UserService {

    private UserDao dao;

    public UserService() {
        this.dao = new UserDao();
    }

    public List<User> findAll() {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findAll();
        });
    }

    public User findById(long id) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findById(id);
        });
    }

    public User findByName(String username) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findByUsername(username);
        });
    }

    public void create(User chatMessage) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.create(chatMessage);
        });
    }

    public void update(User chatMessage) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.update(chatMessage);
        });
    }

    public void delete(long id) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.delete(id);
        });
    }
}
