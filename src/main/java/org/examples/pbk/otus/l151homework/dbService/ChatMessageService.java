package org.examples.pbk.otus.l151homework.dbService;

import org.examples.pbk.otus.l151homework.entity.ChatMessage;

import java.util.List;

public class ChatMessageService {

    private ChatMessageDao dao;

    public ChatMessageService() {
        this.dao = new ChatMessageDao();
    }

    public List<ChatMessage> findAll() {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findAll();
        });
    }

    public ChatMessage findById(long id) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findById(id);
        });
    }

    public void create(ChatMessage chatMessage) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.create(chatMessage);
        });
    }

    public void update(ChatMessage chatMessage) {
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
