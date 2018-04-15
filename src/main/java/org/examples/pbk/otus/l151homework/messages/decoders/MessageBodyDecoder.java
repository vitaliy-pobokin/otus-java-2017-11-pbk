package org.examples.pbk.otus.l151homework.messages.decoders;

import org.examples.pbk.otus.l151homework.messages.MessageBody;
import org.examples.pbk.otus.l151homework.messages.MessageToDbBody;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MessageBodyDecoder {

    private Logger logger = Logger.getLogger("MessageBodyDecoder");

    private Map<String, String> messageMap;

    public MessageBody decode(String s) {
        logger.info("Decoding messageBody: " + s);
        MessageBody messageBody = null;
        if (willDecode(s)) {
            messageBody = new MessageToDbBody(messageMap.get("entity"),
                                                messageMap.get("method"),
                                                messageMap.get("subject"));
        } else {
            logger.info("Unknown messageBody type: " + s);
        }
        return messageBody;
    }

    private boolean willDecode(String s) {
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
        String[] msgToDbKeys = {"entity", "method", "subject"};
        if ((messageMap.keySet().containsAll(Arrays.asList(msgToDbKeys)))) {
            willDecode = true;
        }
        return willDecode;
    }
}
