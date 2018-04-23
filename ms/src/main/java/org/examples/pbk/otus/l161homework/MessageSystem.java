package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.messageSystem.Address;
import org.examples.pbk.otus.l161homework.messageSystem.MessageEndpoint;
import org.examples.pbk.otus.l161homework.messageSystem.MsMessage;
import org.examples.pbk.otus.l161homework.messageSystem.SocketMessageHandler;
import org.examples.pbk.otus.l161homework.messageSystem.exceptions.MessageSystemException;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystem {
    private static final int SERVER_PORT = 5050;
    private static final int CONNECTION_HANDLER_POOL_SIZE = 1;

    private Map<Address, MessageEndpoint> endpoints;
    private Map<MessageEndpoint, Queue<MsMessage>> messages;
    private final List<Thread> workers;

    private ServerSocket serverSocket;
    private final ExecutorService executor;
    private final List<SocketMessageHandler> handlers = new ArrayList<>();

    public MessageSystem() {
        this.endpoints = new ConcurrentHashMap<>();
        this.messages = new ConcurrentHashMap<>();
        this.workers = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(CONNECTION_HANDLER_POOL_SIZE);
    }

    public void init() {
        this.serverSocket = new ServerSocket(SERVER_PORT);
        executor.execute(() -> {
            while (!executor.isTerminated()) {
                Socket socket = serverSocket.accept();
                SocketMessageHandler socketMessageHandler = new SocketMessageHandler(socket);
                handlers.add(socketMessageHandler);
                socketMessageHandler.start();
            }
        });
    }

    public void registerEndpoint(MessageEndpoint endpoint) throws MessageSystemException {
        if (this.endpoints.putIfAbsent(endpoint.getAddress(), endpoint) != null) {
            throw new MessageSystemException("Endpoint with address: " + endpoint.getAddress() + " already registered.");
        }
        Queue<MsMessage> messageQueue = new ConcurrentLinkedQueue<>();
        messages.put(endpoint, messageQueue);
        addWorkerThreadForEndpoint(endpoint);
    }

    public void sendMessage(MsMessage message) throws MessageSystemException {
        MessageEndpoint destination = endpoints.get(message.getTo());
        if (destination != null) {
            Queue<MsMessage> messageQueue = messages.get(destination);
            messageQueue.add(message);
        } else {
            throw new MessageSystemException("Endpoint with address: " + destination.getAddress() + " not registered.");
        }
    }

    public void dispose() {
        synchronized (workers) {
            workers.forEach(Thread::interrupt);
        }
    }

    private void addWorkerThreadForEndpoint(MessageEndpoint endpoint) {
        String threadName = "MessageEndpoint[" + endpoint.getAddress() + "] worker thread";
        Thread thread = new Thread(() -> {
            while (true) {
                Queue<MsMessage> messageQueue = messages.get(endpoint);
                while (!messageQueue.isEmpty()) {
                    endpoint.handle(messageQueue.poll());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        });
        thread.setName(threadName);
        synchronized (workers) {
            workers.add(thread);
        }
        thread.start();
    }
}
