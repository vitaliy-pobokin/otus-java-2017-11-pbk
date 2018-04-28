package org.examples.pbk.otus.l161homework.msMessages;

import org.examples.pbk.otus.l161homework.app.ChatMessageService;
import org.examples.pbk.otus.l161homework.app.MsgToChatMessageService;
import org.examples.pbk.otus.l161homework.entity.ChatMessage;
import org.examples.pbk.otus.l161homework.messageSystem.Address;

import java.io.Serializable;

public class MsgPersistChatMessage extends MsgToChatMessageService implements Serializable {

    private static final long serialVersionUID = -3505165169932209945L;

    private ChatMessage chatMessage;

    public MsgPersistChatMessage(Address from, Address to, ChatMessage chatMessage) {
        super(from, to);
        this.chatMessage = chatMessage;
    }

    @Override
    public void execute(ChatMessageService chatMessageService) {
        chatMessageService.create(chatMessage);
    }
}
