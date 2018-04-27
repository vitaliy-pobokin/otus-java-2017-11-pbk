package org.examples.pbk.otus.l161homework.userService;

import org.examples.pbk.otus.l161homework.app.MessageSystemContext;
import org.examples.pbk.otus.l161homework.app.UserService;
import org.examples.pbk.otus.l161homework.entity.User;
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

public class UserServiceImpl implements UserService {
    private static final int THREAD_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserDao dao;
    private final Address address;
    private final MessageSystemContext context;
    private final ExecutorService executor;

    private Socket socket;
    private MessageHandler messageHandler;

    public UserServiceImpl(Address address, MessageSystemContext context) {
        this.dao = new UserDao();
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

    public void create(User user) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.create(user);
        });
    }

    public void update(User user) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.update(user);
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
