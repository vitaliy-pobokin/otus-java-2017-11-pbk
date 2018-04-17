package org.examples.pbk.otus.l151homework.frontend.encoders;

import org.examples.pbk.otus.l151homework.frontend.messages.AuthWsMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class AuthWsMessageEncoder implements Encoder.Text<AuthWsMessage> {
    @Override
    public String encode(AuthWsMessage authWsMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)){
            jsonGenerator.writeStartObject()
                    .write("type", "auth")
                    .write("username", authWsMessage.getUsername())
                    .write("success", authWsMessage.isSuccess())
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
