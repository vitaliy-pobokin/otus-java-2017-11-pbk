package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.entity.User;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

public interface FrontendService extends Service {

    void init();

    void dispose();

    void makeAuthenticationRequest(String username, String password);

    void handleAuthenticationResponse(String username, boolean success);

    void makeRegistrationRequest(User user);

    void handleRegistrationResponse(User user, boolean success);

    void handleChatMessage(ChatMessage chatMessage);
}
