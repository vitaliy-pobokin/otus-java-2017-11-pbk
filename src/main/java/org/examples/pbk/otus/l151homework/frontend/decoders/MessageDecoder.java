package org.examples.pbk.otus.l151homework.frontend.decoders;

import org.examples.pbk.otus.l151homework.frontend.messages.ChatMessage;
import org.examples.pbk.otus.l151homework.frontend.messages.JoinMessage;
import org.examples.pbk.otus.l151homework.frontend.messages.Message;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MessageDecoder implements Decoder.Text<Message> {

    private Logger logger = Logger.getLogger("MessageDecoder");

    private Map<String, String> messageMap;

    @Override
    public Message decode(String s) throws DecodeException {
        logger.info("Decoding message: " + s);
        Message message = null;
        if (willDecode(s)) {
            switch (messageMap.get("type")) {
                case "join":
                    message = new JoinMessage(messageMap.get("user"));
                    break;
                case "chat":
                    message = new ChatMessage(
                            messageMap.get("from"),
                            messageMap.get("to"),
                            messageMap.get("text")
                        );
                    break;
            }
        } else {
            logger.info("Unknown message type: " + s);
            throw new DecodeException(s, "Cannot be decoded.");
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        boolean willDecode = false;
        messageMap = new HashMap<>();
        JsonParser parser = Json.createParser(new StringReader(s));
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.KEY_NAME){
                String key = parser.getString();
                parser.next();
                String value = parser.getString();
                messageMap.put(key, value);
            }
        }
        if (messageMap.containsKey("type")) {
            switch (messageMap.get("type")) {
                case "join":
                    if (messageMap.containsKey("user"))
                        willDecode = true;
                    break;
                case "chat":
                    String[] chatMsgKeys = {"from", "to", "text"};
                    if (messageMap.keySet().containsAll(Arrays.asList(chatMsgKeys)))
                        willDecode = true;
                    break;
            }
        }
        return willDecode;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
