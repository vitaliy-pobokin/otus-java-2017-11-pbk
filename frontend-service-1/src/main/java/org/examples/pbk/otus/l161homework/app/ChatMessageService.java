package org.examples.pbk.otus.l161homework.app;

import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.messageSystem.Service;

import java.util.List;

public interface ChatMessageService extends Service {
    void init();

    List<ChatMessage> findAll();

    ChatMessage findById(long id);

    void create(ChatMessage user);

    void update(ChatMessage user);

    void delete(long id);
}
