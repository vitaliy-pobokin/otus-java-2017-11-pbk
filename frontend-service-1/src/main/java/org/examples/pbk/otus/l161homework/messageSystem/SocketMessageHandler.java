package org.examples.pbk.otus.l161homework.messageSystem;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageHandler implements MessageHandler {
    private static final int THREAD_POOL_SIZE = 2;
    private static final Logger logger = Logger.getLogger(SocketMessageHandler.class.getName());

    private final BlockingQueue<MsMessage> outMsgQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<MsMessage> inMsgQueue = new LinkedBlockingQueue<>();

    private final Socket socket;
    private final ExecutorService executor;

    public SocketMessageHandler(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Override
    public void start() {
        executor.execute(this::getFromSocket);
        executor.execute(this::sendToSocket);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public MsMessage getMessage() {
        try {
            return inMsgQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(MsMessage message) throws IllegalStateException {
        this.outMsgQueue.add(message);
    }

    private void sendToSocket() {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            while (socket.isConnected()) {
                MsMessage message = outMsgQueue.take();
                out.writeObject(message);
                out.flush();
                logger.log(Level.INFO, "Message: " + message + " was sent.");
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void getFromSocket() {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
            while (socket.isConnected()) {
                MsMessage message = (MsMessage) in.readObject();
                inMsgQueue.put(message);
                logger.log(Level.INFO, "Message: " + message + " was received.");
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
