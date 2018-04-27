package org.examples.pbk.otus.l161homework;

import java.io.IOException;

public class MessageServiceRunner {
    public static void main(String[] args) {
        MessageService messageService = new MessageService();
        try {
            messageService.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
