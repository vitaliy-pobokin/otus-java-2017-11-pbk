package org.examples.pbk.otus.l161homework;

import org.examples.pbk.otus.l161homework.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServiceRunner {
    private static final Logger logger = Logger.getLogger(MessageServiceRunner.class.getName());

    private static final String USER_SERVICE_START_COMMAND = "java -jar ../user-service/target/user-service.jar";
    private static final int USER_SERVICE_START_DELAY_SEC = 1;
    private static final String CHAT_MESSAGE_SERVICE_START_COMMAND = "java -jar ../chat-message-service/target/chat-message-service.jar";
    private static final int CHAT_MESSAGE_SERVICE_START_DELAY_SEC = 5;
    private static final String FRONTEND_SERVICE_1_START_COMMAND = "java -jar ../frontend-service-1/target/frontend-service-1.jar";
    private static final int FRONTEND_SERVICE_1_START_DELAY_SEC = 10;
    private static final String FRONTEND_SERVICE_2_START_COMMAND = "java -jar ../frontend-service-2/target/frontend-service-2.jar";
    private static final int FRONTEND_SERVICE_2_START_DELAY_SEC = 10;

    public static void main(String[] args) throws Exception {
        new MessageServiceRunner().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        MessageService messageService = new MessageService();
        messageService.init();

        startClientService(executorService, USER_SERVICE_START_COMMAND, USER_SERVICE_START_DELAY_SEC);
        startClientService(executorService, CHAT_MESSAGE_SERVICE_START_COMMAND, CHAT_MESSAGE_SERVICE_START_DELAY_SEC);
        startClientService(executorService, FRONTEND_SERVICE_1_START_COMMAND, FRONTEND_SERVICE_1_START_DELAY_SEC);
        startClientService(executorService, FRONTEND_SERVICE_2_START_COMMAND, FRONTEND_SERVICE_2_START_DELAY_SEC);

        executorService.shutdown();
    }

    private void startClientService(ScheduledExecutorService executorService, String startCommand, int delay) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(startCommand);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, delay, TimeUnit.SECONDS);
    }
}
