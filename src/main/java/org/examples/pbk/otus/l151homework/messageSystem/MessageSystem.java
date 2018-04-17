package org.examples.pbk.otus.l151homework.messageSystem;

import org.examples.pbk.otus.l151homework.messageSystem.exceptions.MessageSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private Map<Address, MessageEndpoint> endpoints;
    private Map<MessageEndpoint, Queue<MsMessage>> messages;
    private final List<Thread> workers;

    public MessageSystem() {
        this.endpoints = new ConcurrentHashMap<>();
        this.messages = new ConcurrentHashMap<>();
        this.workers = new ArrayList<>();
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
