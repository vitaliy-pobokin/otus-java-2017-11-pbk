package org.examples.pbk.otus.l161homework;

import java.io.IOException;

public class MessageSystemRunner {
    public static void main(String[] args) {
        MessageSystem messageSystem = new MessageSystem();
        try {
            messageSystem.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
