package org.examples.pbk.otus.l161homework.frontend.decoders;

import org.examples.pbk.otus.l161homework.frontend.wsMessages.ChatWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.LoginWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.RegisterWsMessage;
import org.examples.pbk.otus.l161homework.frontend.wsMessages.MsgFromWebsocket;

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

public class WsMessageDecoder implements Decoder.Text<MsgFromWebsocket> {

    private Logger logger = Logger.getLogger("WsMessageDecoder");

    private Map<String, String> messageMap;

    @Override
    public MsgFromWebsocket decode(String s) throws DecodeException {
        logger.info("Decoding message: " + s);
        MsgFromWebsocket message = null;
        if (willDecode(s)) {
            switch (messageMap.get("type")) {
                case "chat":
                    message = new ChatWsMessage(
                            messageMap.get("from"),
                            messageMap.get("to"),
                            messageMap.get("text"));
                    break;
                case "login":
                    message = new LoginWsMessage(
                            messageMap.get("username"),
                            messageMap.get("password"));
                    break;
                case "register":
                    message = new RegisterWsMessage(
                            messageMap.get("username"),
                            messageMap.get("password"));
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
                case "chat":
                    String[] chatMsgKeys = {"from", "to", "text"};
                    if (messageMap.keySet().containsAll(Arrays.asList(chatMsgKeys)))
                        willDecode = true;
                    break;
                case "login":
                    String[] loginMsgKeys = {"username", "password"};
                    if (messageMap.keySet().containsAll(Arrays.asList(loginMsgKeys)))
                        willDecode = true;
                    break;
                case "register":
                    String[] registerMsgKeys = {"username", "password"};
                    if (messageMap.keySet().containsAll(Arrays.asList(registerMsgKeys)))
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
