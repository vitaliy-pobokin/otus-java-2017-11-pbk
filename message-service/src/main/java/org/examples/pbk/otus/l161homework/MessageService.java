package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l161homework.messageSystem.SocketMessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageService {
    private static final int SERVER_PORT = 5050;
    private static final int CONNECTION_HANDLER_POOL_SIZE = 1;
    private static final Logger logger = Logger.getLogger(MessageService.class.getName());

    private ConcurrentHashMap<Address, SocketMessageHandler> endpoints;
    private final List<Thread> workers;

    private ServerSocket serverSocket;
    private final ExecutorService executor;

    public MessageService() {
        this.endpoints = new ConcurrentHashMap<>();
        this.workers = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(CONNECTION_HANDLER_POOL_SIZE);
    }

    public void init() throws IOException {
        this.serverSocket = new ServerSocket(SERVER_PORT);
        logger.log(Level.INFO, "Server started on port: " + SERVER_PORT);
        executor.execute(this::handleConnection);
    }

    private void handleConnection() {
        while (!executor.isTerminated()) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error while handling socket connection: " + e.getMessage());
                continue;
            }
            SocketMessageHandler socketMessageHandler = new SocketMessageHandler(socket);
            Address endpointAddress = new Address(socket.getLocalAddress(), socket.getPort());
            registerEndpoint(endpointAddress, socketMessageHandler);
            socketMessageHandler.start();
            logger.log(Level.INFO, "Accepted connection from endpoint: " + endpointAddress.toString());
        }
    }

    private void registerEndpoint(Address address, SocketMessageHandler messageHandler) {
        this.endpoints.put(address, messageHandler);
        addWorkerThreadForEndpoint(address, messageHandler);
    }

    private void sendMessage(MsMessage message) {
        SocketMessageHandler destination = endpoints.get(message.getTo());
        if (destination != null) {
            destination.sendMessage(message);
        } else {
            logger.log(Level.WARNING, "Endpoint with address: " + message.getTo() + " not registered.");
        }
    }

    public void dispose() {
        executor.shutdown();
        synchronized (workers) {
            workers.forEach(Thread::interrupt);
        }
        endpoints.values().forEach(SocketMessageHandler::shutdown);
        logger.log(Level.INFO, "Server stopped on port: " + SERVER_PORT);
    }

    private void addWorkerThreadForEndpoint(Address address, SocketMessageHandler messageHandler) {
        String threadName = "MessageEndpoint[" + address + "] worker thread";
        Thread thread = new Thread(() -> {
            while (true) {
                MsMessage message = messageHandler.getMessage();
                sendMessage(message);
                if (Thread.currentThread().isInterrupted()) {
                    logger.log(Level.INFO, "Thread: " + Thread.currentThread().getName() + " was interrupted.");
                    return;
                }
            }
        });
        thread.setName(threadName);
        synchronized (workers) {
            workers.add(thread);
        }
        thread.start();
        logger.log(Level.INFO, "Thread: " + thread.getName() + " started.");
    }
}
