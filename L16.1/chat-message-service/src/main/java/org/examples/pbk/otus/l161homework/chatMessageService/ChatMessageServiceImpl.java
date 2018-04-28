package org.examples.pbk.otus.l161homework.chatMessageService;

import org.examples.pbk.otus.l161homework.app.ChatMessageService;
import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageHandler;
import org.examples.pbk.otus.l161homework.messageSystem.SocketMessageHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatMessageServiceImpl implements ChatMessageService {
    private static final int THREAD_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger(ChatMessageServiceImpl.class.getName());

    private ChatMessageDao dao;
    private final Address address;
    private final MessageSystemContext context;
    private final ExecutorService executor;

    private Socket socket;
    private MessageHandler messageHandler;

    public ChatMessageServiceImpl(Address address, MessageSystemContext context) {
        this.dao = new ChatMessageDao();
        this.address = address;
        this.context = context;
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Override
    public void init() {
        try {
            this.socket = new Socket(
                    context.getMessageSystemAddress().getInetAddress(),
                    context.getMessageSystemAddress().getPort(),
                    address.getInetAddress(),
                    address.getPort());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while establishing socket connection: " + e.getMessage());
        }
        this.messageHandler = new SocketMessageHandler(socket);
        messageHandler.start();

        executor.execute(() -> {
            while (!executor.isTerminated()) {
                messageHandler.getMessage().execute(this);
            }
        });
    }

    public void dispose() {
        messageHandler.shutdown();
        executor.shutdown();
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while closing socket connection: " + e.getMessage());
        }
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystemContext getContext() {
        return context;
    }

    @Override
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
