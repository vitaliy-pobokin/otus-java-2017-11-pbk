package org.examples.pbk.otus.l151homework.frontend.encoders;

import org.examples.pbk.otus.l151homework.frontend.messages.JoinWsMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class JoinWsMessageEncoder implements Encoder.Text<JoinWsMessage> {
    @Override
    public String encode(JoinWsMessage joinMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject()
                    .write("type", "join")
                    .write("user", joinMessage.getUser())
                .writeEnd();
        }
        return stringWriter.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
